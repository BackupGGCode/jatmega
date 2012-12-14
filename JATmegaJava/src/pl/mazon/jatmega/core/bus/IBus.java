package pl.mazon.jatmega.core.bus;


public interface IBus {

	void init(IBusConfig config);
	
	boolean send(byte[] message);
	
	void addReceiverCallback(IBusReceiveCallback callback);
	
	void addEventCallback(IBusEventCallback callback);
	
	boolean isOnLine();
	
	void connect();
	
	void disconnect();

	void setIsAutoConnect(boolean isAutoConnect);

}
