package pl.mazon.jatmega.core.bus;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter IBus. Implementuje system rozgloszeniowy callback
 * @author radomir.mazon
 *
 */

public abstract class BusAdapter implements IBus {

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
