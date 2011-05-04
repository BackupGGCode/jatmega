package pl.mazon.jatmega.core.bus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.mazon.jatmega.logger.LogFactory;
import pl.mazon.jatmega.logger.Logger;

/**
 * Adapter IBus. Implementuje system rozgloszeniowy callback
 * @author radomir.mazon
 *
 */

public abstract class BusAdapter implements IBus {

	private boolean initResult;
	
	private boolean isAutoConnect;
	
	private Logger logger = LogFactory.getLog(BusAdapter.class);
	
	/**
	 * Znacznik nowej linii (czasami jest '\r')
	 * czasami występują jednocześnie. 
	 * W tym driverze używany jest jeden z nich
	 */
	public static final char EOL = '\r';
	
	private List<IBusReceiveCallback> receiveCallbackList;
	
	private List<IBusEventCallback> eventCallbackList;
	
	
	public BusAdapter() {
		receiveCallbackList = new ArrayList<IBusReceiveCallback>();
		eventCallbackList = new ArrayList<IBusEventCallback>();
		initResult = false;
		isAutoConnect = true;
	}
	
	@Override
	public boolean isOnLine() {
		if (initResult == false) {
			if (isAutoConnect) {
				connect();
			}
		}
		return initResult;
	}
	
	@Override
	public boolean sendLine(String message) {
		if (!isOnLine()) {
			return false;
		}
		 try {
			 sendLineInternal(message);
       } catch (IOException e) {
    	   logger.info("Can't write to port... Disconnect...");
    	   disconnect();
    	   return false;
       } 
       return true;
	}
	
	@Override
	public boolean send(String message) {
		if (!isOnLine()) {
			return false;
		}
		 try {
			 sendInternal(message);
       } catch (IOException e) {
    	   logger.info("Can't write to port... Disconnect...");
    	   disconnect();
    	   return false;
       } 
       return true;
	}
	
	abstract protected void sendLineInternal(String message) throws IOException;

	abstract protected void sendInternal(String message) throws IOException;
	
	@Override
	public void connect() {
		initResult = connectInternal();
		if (initResult) {
			logger.debug("Bus connected success.");
			onConnectEvent();
		} else {
			logger.debug("Bus connection failure.");
			onDisconnectEvent();
		}
	}
	
	@Override
	public void disconnect() {
		initResult = false;
		disconnectInternal();
		logger.info("Bus disconnected.");
		onDisconnectEvent();
	}
	
	@Override
	public void setIsAutoConnect(boolean isAutoConnect) {
		this.isAutoConnect = isAutoConnect;
	}
	
	abstract protected boolean connectInternal();
	
	abstract protected void disconnectInternal();
	
	protected void onReceive(String message) {
		for (IBusReceiveCallback callback : receiveCallbackList) {
			callback.receiveLine(message);
		}
	}
	
	protected void onConnectEvent() {
		for (IBusEventCallback eventCallback : eventCallbackList) {
			eventCallback.connectEvent();
		}
	}
	
	protected void onDisconnectEvent() {
		for (IBusEventCallback eventCallback : eventCallbackList) {
			eventCallback.disconnectEvent();
		}
	}

	protected void onDriverLoadFailureEvent() {
		for (IBusEventCallback eventCallback : eventCallbackList) {
			eventCallback.driverLoadFailuerEvent();
		}
	}
	
	protected void onOperationFailureBusConnecting() {
		for (IBusEventCallback eventCallback : eventCallbackList) {
			eventCallback.operationFailureBusConnecting();
		}
	}
	@Override
	public void addReceiverCallback(IBusReceiveCallback callback) {
		receiveCallbackList.remove(callback);
		receiveCallbackList.add(callback);
	}
	
	@Override
	public void addEventCallback(IBusEventCallback callback) {
		eventCallbackList.remove(callback);
		eventCallbackList.add(callback);
	}
}
