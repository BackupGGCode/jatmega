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

	protected boolean initResult;
	
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
	}
	
	@Override
	public boolean isOnLine() {
		if (initResult == false) {
			connect();
		}
		return initResult;
	}
	
	public void sendLine(String message) {
		if (!isOnLine()) {
			logger.info("Bus is offline! Can't send.");
			return;
		}
		 try {
			 sendLineInternal(message);
       } catch (IOException e) {
    	   logger.info("Can't write to port... Disconnect...");
    	   disconnect();
       } 
	}
	
	@Override
	public void send(String message) {
		if (!isOnLine()) {
			logger.info("Bus is offline! Can't send.");
			return;
		}
		 try {
			 sendInternal(message);
       } catch (IOException e) {
    	   logger.info("Can't write to port... Disconnect...");
    	   disconnect();
       } 
	}
	
	abstract protected void sendLineInternal(String message) throws IOException;

	abstract protected void sendInternal(String message) throws IOException;
	
	@Override
	public void connect() {
		initResult = false;
		connectInternal();
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
		logger.info("Bus disconnection.");
		onDisconnectEvent();
	}
	
	abstract protected void connectInternal();
	
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
