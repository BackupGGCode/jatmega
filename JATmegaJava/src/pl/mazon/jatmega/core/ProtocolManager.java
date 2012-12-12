package pl.mazon.jatmega.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import pl.mazon.jatmega.ConvertHelper;
import pl.mazon.jatmega.core.bus.IBus;
import pl.mazon.jatmega.core.bus.IBusReceiveCallback;
import pl.mazon.jatmega.core.command.CommandFactory;
import pl.mazon.jatmega.core.command.ICommand;
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

	private static final long TIME_OUT_COMMAND = 10000;
	
	/**
	 *	Przechowuje informacje o czasie wyslanego pytania 
	 *  tylko dla pytan wymagajacych odpowiedzi.
	 */
	class ProtocolInfo {
		@SuppressWarnings("rawtypes")
		private ICommand command;
		private byte targetName;
		private byte frameId;
		private long timestamp;
		private long timeout;
		@SuppressWarnings("rawtypes")
		public ProtocolInfo(byte targetName, byte frameId, ICommand command) {
			this.targetName = targetName;
			this.frameId = frameId;
			this.timestamp = new Date().getTime();
			this.timeout = TIME_OUT_COMMAND;
			this.command = command;
		}
		
		public long getTimestamp() {
			return timestamp;
		}
		
		public boolean isTimeout() {
			return new Date().getTime() > (timestamp + timeout);
		}
		
		public byte getTargetName() {
			return targetName;
		}
		
		@SuppressWarnings("rawtypes")
		public ICommand getCommand() {
			return command;
		}
		
		public byte getFrameId() {
			return frameId;
		}
		
		@Override
		public String toString() {
			return "[c:" + targetName + ", id:"+ConvertHelper.byteToHex(frameId)+"]";
		}
		
	}
	
	private static ProtocolManager instance;
	
	/**
	 * przechowuje komendy wykonywanie synchronicznie
	 */
	private List<ProtocolInfo> syncCommandList;
	
	/**
	 * Magistrala komunikacyjna
	 */
	private IBus bus;
	
	/**
	 * Lista odebranych wiadomosci
	 */
	private List<Byte[]> receiveMessageList;
	
	private byte gframeId = 0;
	
	private Logger logger = LogFactory.getLog(ProtocolManager.class);
	
	private ProtocolManager() {		
		super("ProtocolManager");
		syncCommandList = new ArrayList<ProtocolInfo>();
		receiveMessageList = new ArrayList<Byte[]>();
	}
	
	public static ProtocolManager getInstance() {
		if (instance == null) {
			instance = new ProtocolManager();
		}
		return instance;
	}
	
	private synchronized void putMessage(byte[] message) {
		Byte[] byteTable = new Byte[message.length];
		for (int i=0; i<message.length; i++) {
			byteTable[i] = message[i];
		}
		receiveMessageList.add(byteTable);
	}
	
	private synchronized Byte[] getMessage() {
		if (receiveMessageList.size() == 0){ 
			return null;
		} 
		Byte[] message = receiveMessageList.get(0);
		receiveMessageList.remove(0);
		return message;
		
	}
	
	private synchronized void wakeup() {
		notify();
	}
	
	@Override
	public synchronized void run() {
		
		bus.addReceiverCallback(new IBusReceiveCallback() {
			
			@Override
			public synchronized void receive(byte[] message) {
				putMessage(message);
				wakeup();
			}
		});
		
		boolean run = true;
		while(run) {
			
			//obsługa odebranej wiadomości
			Byte[] message = getMessage();
			if (message != null && message.length > 0) {
				applyMessage(message);
			}
			
			//obsługa timeoutów...
			
			synchronized (syncCommandList) {
				Iterator<ProtocolInfo> it = syncCommandList.iterator();
				while(it.hasNext()) {
					ProtocolInfo protocolInfo = it.next();
					if (protocolInfo.isTimeout()) {
						it.remove();
						logger.debug("Timeout for command: " + protocolInfo);
						try {
							protocolInfo.getCommand().onFailure();
						}catch (Exception e) {
							logger.error(e.getMessage());
						}
					}
				}	
			}
			
			try {
				if (receiveMessageList.size() == 0) {
					wait(TIME_OUT_COMMAND/2);
				}
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
	@SuppressWarnings("rawtypes")
	public synchronized void apply(ICommand command) {
		synchronized (syncCommandList) {
			byte[] message = command.getRequest().serialize();
			byte[] frame = frame(message, command);
			byte targetName = command.getTargetName();
			if (bus.send(frame)) {
				if (command.isResponseMendatory()) {
					ProtocolInfo protocolInfo = new ProtocolInfo(targetName, frame[1] ,command);
					syncCommandList.add(protocolInfo);
				}
				logger.info("send c: " + ConvertHelper.byteToHex(targetName) + " m: " + ConvertHelper.byteArrayToHex(message) + " [" + ConvertHelper.byteArrayToHex(frame) + "]");
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private byte[] frame(byte[] message, ICommand command) {
		int extraByteInFrame = 1;
		if (command.isResponseMendatory()) {
			extraByteInFrame = 2;
		}
		byte[] frame = new byte[message.length + extraByteInFrame];
		byte frameId = 0;
		if (command.isResponseMendatory()) {
			frameId = getFrameId();
		}
		frame[0] = (byte)(command.getTargetName() << 4);
		frame[0] = (byte) (frame[0] | (getSum(message, frameId) & 0x0F));
		if (command.isResponseMendatory()) {
			frame[1] = frameId;
		}
		for (int i=0; i<message.length; i++) {
			frame[i+extraByteInFrame] = message[i];
		}
		return frame;
	}
	
	@SuppressWarnings("rawtypes")
	private byte[] getMessageFromFrame(Byte[] frame, ICommand command) {
		int extraByteInFrame = 1;
		if (command.isResponseMendatory()) {
			extraByteInFrame = 2;
		}
		int messageSize = frame.length - extraByteInFrame;
		byte[] message = new byte[messageSize];
		for (int i=0; i<messageSize; i++) {
			if (command.isResponseMendatory()) {
				message[i] = frame[i+2];	
			} else {
				message[i] = frame[i+1];
			}
		}
		return message;
	}
	
	private byte getFrameId() {
		return gframeId++;
	}
	
	private byte getSum(byte[] message, byte frameId) {
		byte sum = frameId;
		for (int i=0; i<message.length; i++) {
			sum += message[i];
		}
		return sum;
	}
	
	private boolean checkSum(Byte[] frame) {
		byte frameSum = (byte)(frame[0] & 0x0f);
		byte sum = 0;
		for (int i=1; i<frame.length; i++) {
			sum += frame[i];
		}
		return frameSum == (byte)(sum&0x0f);
	}
	
	private byte getTargetName(Byte[] frame) {
		if (frame.length == 0) {
			throw new IllegalStateException("Can't get target name. Frame was empty !");
		}
		return (byte)(((frame[0]) >> 4) & 0x0f);
	}
	private byte getFrameId(Byte[] frame) {
		if (frame.length < 2) {
			throw new IllegalStateException("Can't get target name. Frame was too short !");
		}
		return (byte)(frame[1]);
	}

	/**
	 * Odebrano wiadomość
	 * @param message
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private synchronized void applyMessage(Byte[] frame) {
		if (!checkSum(frame)) {
			logger.error("Wrong sum! f: " + ConvertHelper.ByteArrayToHex(frame) + " dec: " +ConvertHelper.ByteArrayToDec(frame));
		}
		synchronized (syncCommandList) {
			
			byte targetName = getTargetName(frame);
			
			ICommand command = CommandFactory.getCommand(targetName);
			if (command == null) {
				Iterator<ProtocolInfo> it = syncCommandList.iterator();
				while(it.hasNext()) {
					ProtocolInfo info = it.next();
					if (info.getTargetName() == targetName && info.getCommand().isResponseMendatory()) {
						byte frameId = getFrameId(frame);
						if (info.getFrameId() == frameId) {
							command = info.getCommand();
							it.remove();
							break;
						}
					}
				}
			}
			if (command == null) {
				logger.error("Can't find command for frame: " + ConvertHelper.ByteArrayToHex(frame));
				return;
			}
			
			byte[] message = getMessageFromFrame(frame, command);
			if (message == null) {
				logger.error("Can't get message from frame: " + ConvertHelper.ByteArrayToHex(frame));
				return;
			}
			
			logger.info("receive c: " + targetName + " m: " + ConvertHelper.byteArrayToHex(message) + " [" + ConvertHelper.ByteArrayToHex(frame) + "]");

			boolean onSuccess = true;
			try {
				command.getResponse().deserialize(message);
			}catch (Exception e) {
				try {
					logger.error("Deserialize error: " + e.getMessage());
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
}