package pl.mazon.jatmega.core.bus;


public interface IBus {

	void init(IBusConfig config);
	
	void send(String message);
	
	void sendLine(String message);
	
	void addReceiverCallback(IBusReceiveCallback callback);
	
	void addEventCallback(IBusEventCallback callback);
	
	boolean isOnLine();

}
