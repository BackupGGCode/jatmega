package pl.mazon.jatmega.core.bus;


public interface IBus {

	void send(String message);
	
	void sendLine(String message);
	
	boolean isOnLine();

}
