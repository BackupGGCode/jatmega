package pl.mazon.jatmega.core.bus;


public interface IBusEventCallback {

	void connectEvent();

	void disconnectEvent();
	
	void driverLoadFailuerEvent();
}
