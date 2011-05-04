package pl.mazon.jatmega.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pl.mazon.jatmega.core.bus.IBus;
import pl.mazon.jatmega.core.bus.IBusReceiveCallback;
import pl.mazon.jatmega.core.command.ICommand;
import pl.mazon.jatmega.core.model.IModel;
import pl.mazon.jatmega.logger.LogFactory;
import pl.mazon.jatmega.logger.Logger;

/**
 * Menadzer jest wątkiem wysyłającym polecenia do ATMEGA
 * Obsługuje komunikacę szeregowo. Po wysłaniu pytania
 * czeka na odpowiedź.
 * @author radomir.mazon
 *
 */

public class ProtocolManager extends Thread {

	private static final long TIME_OUT_COMMAND = 6000;
	
	class ProtocolInfo {
		private char targetName;
		private char signature;
		private long timestamp;
		private long timeout;
		public ProtocolInfo(char targetName, char signature) {
			this.targetName = targetName;
			this.signature = signature;
			this.timestamp = new Date().getTime();
			this.timeout = TIME_OUT_COMMAND;
		}
		
		public boolean isTimeout() {
			return new Date().getTime() > (timestamp + timeout);
		}
		
		public char getTargetName() {
			return targetName;
		}
		
		public char getSignature() {
			return signature;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + signature;
			result = prime * result + targetName;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ProtocolInfo other = (ProtocolInfo) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (signature != other.signature)
				return false;
			if (targetName != other.targetName)
				return false;
			return true;
		}

		private ProtocolManager getOuterType() {
			return ProtocolManager.this;
		}
		
		@Override
		public String toString() {
			return "[" + targetName + signature + "]";
		}
		
	}
	
	private static ProtocolManager instance;
	
	/**
	 * przechowuje aktualnie wyslana komende
	 * (odebrane sa jeszcze nie oprogramowane)
	 */
	private Map<ProtocolInfo, ICommand> commandMap;
	
	/**
	 * Licznik sygnatur, dla namego targetName
	 */
	private Map<Character, Character> signatureMap;
	
	private IBus bus;
	
	private List<String> receiveMessage;
	
	private Logger logger = LogFactory.getLog(ProtocolManager.class);
	
	private ProtocolManager() {		
		super("ProtocolManager");
		commandMap = new HashMap<ProtocolInfo, ICommand>();
		receiveMessage = new ArrayList<String>();
		signatureMap = new HashMap<Character, Character>();
	}
	
	public static ProtocolManager getInstance() {
		if (instance == null) {
			instance = new ProtocolManager();
		}
		return instance;
	}
	
	private synchronized void putMessage(String message) {
		receiveMessage.add(message);
	}
	
	private synchronized String getMessage() {
		if (receiveMessage.size() == 0){ 
			return null;
		} 
		String message = receiveMessage.get(0);
		receiveMessage.remove(0);
		return message;
		
	}
	
	private synchronized void wakeup() {
		notify();
	}
	
	@Override
	public synchronized void run() {
		
		//final ProtocolManager thisPointer = this;
		bus.addReceiverCallback(new IBusReceiveCallback() {
			
			@Override
			public synchronized void receiveLine(String message) {
				putMessage(message);
				wakeup();
			}
		});
		
		boolean run = true;
		while(run) {
			
			//obsługa odebranej wiadomości
			String message = getMessage();
			if (message != null && message.length() > 3) {
				applyMessage(message);
			}
			
			//obsługa timeoutów...
			
			synchronized (commandMap) {
				Iterator<ProtocolInfo> it = commandMap.keySet().iterator();
				while (it.hasNext())
				{
					ProtocolInfo protocolInfo = it.next();
					if (protocolInfo.isTimeout()) {
						ICommand command = commandMap.get(protocolInfo);
						it.remove();
						logger.debug("Timeout for command: " + protocolInfo);
						try {
							command.onFailure();
						}catch (Exception e) {}	
					}
					
				}	
			}
		
			
			try {
				wait(TIME_OUT_COMMAND);
			} catch (InterruptedException e) {
				logger.error("Protocol manager stopped.");
				run = false;
			}
		}
		
	}

	public void setBus(IBus bus) {
		if (this.bus == null) {
			this.bus = bus;
			start();
		} else {
			if (this.bus != bus) {
				throw new IllegalStateException("Protocol manager not support multi bus.");
			}
		}
		
	}

	
	/**
	 * Metoda dodaje protokuł do listy wykonywanych przez wątek ProtocolManager
	 * Protokół umie się serializować i deserializować. OnFailure wykonywane jest
	 * gdy nasąpi timeout odpowiedzi. Odpowiedź jest potwierdzeniem. Niesie też informację zwrotną,
	 * która jest przekazywana w onSuccess jako parametr.
	 * @param testProtocol
	 */
	public synchronized void apply(ICommand command) {
		synchronized (commandMap) {
			char targetName = command.getTargetName();
			char signature = getNextFreeSirgature(targetName);
			ProtocolInfo key = new ProtocolInfo(targetName, signature);
			IModel request = command.getRequest();
			String message = "" + key.getTargetName() + key.getSignature() + request.toString();
			if (bus.sendLine(message)) {
				commandMap.put(key, command);
				logger.info("send: "+ message);
			}
		}
	}
	
	/**
	 * Odebrano wiadomość
	 * @param message
	 */
	private synchronized void applyMessage(String message) {
		synchronized (commandMap) {
			boolean onSuccess = true;
			logger.info("receive: " + message);
			ProtocolInfo protocolInfo = new ProtocolInfo(message.charAt(0), message.charAt(1));
			ICommand command = commandMap.get(protocolInfo);
			if (command == null) {
				logger.error("No command for: " + message);
				return;
			}
			commandMap.remove(protocolInfo);
			message = message.substring(2);
			try {
				command.getResponse().fromString(message);
			}catch (Exception e) {
				try {
					command.onFailure();
				} catch (Exception ee) {}
				onSuccess = false;
			}
			if (onSuccess) {
				try {
					command.onSuccess(command.getResponse());
				}catch (Exception e) {}
			}
		}
	}

	
	private char getNextFreeSirgature(char targetName) {
		Character sig = signatureMap.get(new Character(targetName));
		if (sig == null) {
			sig = '0';
		}
		if (sig.equals('z')) {
			sig = '0';
		}
		sig++;
		signatureMap.put(targetName, sig);
		return sig.charValue();
	}
}

	