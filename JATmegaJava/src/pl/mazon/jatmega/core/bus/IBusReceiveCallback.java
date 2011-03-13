package pl.mazon.jatmega.core.bus;

/**
 * 
 * @author radomir.mazon
 *
 */

public interface IBusReceiveCallback {

	//Teraz jeszcze nie potrzebne...
	//void receive(char message);
	
	void receiveLine(String message);
	
}
