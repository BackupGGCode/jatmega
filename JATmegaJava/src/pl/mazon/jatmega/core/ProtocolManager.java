package pl.mazon.jatmega.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mazon.jatmega.core.bus.IBus;
import pl.mazon.jatmega.core.bus.IBusReceiveCallback;
import pl.mazon.jatmega.core.command.ICommand;
import pl.mazon.jatmega.core.model.IModel;

/**
 * Menadzer jest wątkiem wysyłającym polecenia do ATMEGA
 * Obsługuje komunikacę szeregowo. Po wysłaniu pytania
 * czeka na odpowiedź.
 * @author radomir.mazon
 *
 */

public class ProtocolManager extends Thread {

	private static final long TIME_OUT_COMMAND = 4000;
	
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
			return "[" + targetName + signature + "]" + new Date().getTime() + "/" + (timestamp + timeout) +"/"+ isTimeout();
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
	
	/**
	 * Przechowuje numer ostatnio wygenerowanej komendy
	 */
	private Map<String, String> counterMap;
	
	private IBus bus;
	
	private List<String> receiveMessage;
	
	private Log logger = LogFactory.getLog(ProtocolManager.class);
	
	private ProtocolManager() {		
		super("ProtocolManager");
		commandMap = new HashMap<ProtocolInfo, ICommand>();
		counterMap = new HashMap<String, String>();
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
		final ProtocolManager thisPointer = this;
		bus.addReceiverCallback(new IBusReceiveCallback() {
			
			@Override
			public void receiveLine(String message) {
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
		
			for (ProtocolInfo protocolInfo : commandMap.keySet()) {
				if (protocolInfo.isTimeout()) {
					ICommand command = commandMap.get(protocolInfo);
					commandMap.remove(protocolInfo);	
					logger.debug("Timeout for command: " + protocolInfo);
					command.onFailure();
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
	public void apply(ICommand command) {
		char targetName = command.getTargetName();
		char signature = getNextFreeSirgature(targetName);
		ProtocolInfo key = new ProtocolInfo(targetName, signature);
		commandMap.put(key, command);
		IModel request = command.getRequest();
		String message = "" + key.getTargetName() + key.getSignature() + request.toString();
		bus.sendLine(message);
	}
	
	/**
	 * Odebrano wiadomość
	 * @param message
	 */
	private synchronized void applyMessage(String message) {
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
			command.onFailure();
			onSuccess = false;
		}
		if (onSuccess) {
			command.onSuccess(command.getResponse());
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
