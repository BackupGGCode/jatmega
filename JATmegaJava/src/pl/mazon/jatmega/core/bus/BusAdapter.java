package pl.mazon.jatmega.core.bus;

import java.util.ArrayList;
import java.util.List;

import pl.mazon.jatmega.core.bus.event.IBusEvent;

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
	public static final char EOL = '\n';
	
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
	
	protected void onEvent(IBusEvent event) {
		for (IBusEventCallback eventCallback : eventCallbackList) {
			eventCallback.eventCallback(event);
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
