package pl.mazon.jatmega.main;

import pl.mazon.jatmega.core.BusManager;
import pl.mazon.jatmega.core.ProtocolManager;
import pl.mazon.jatmega.core.bus.IBus;
import pl.mazon.jatmega.core.bus.IBusEventCallback;

/**
 * 
 * @author radomir.mazon
 *
 */

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ProtocolManager protocolManager = ProtocolManager.getInstance();
		
		IBus bus = BusManager.getInstance().getBus();

		//menadzer protokolu musi miec przez co sie komunikowac.
		protocolManager.setBus(bus);
		
		
		
		//test powiadomień z Bus
		bus.addEventCallback(new IBusEventCallback() {
			
			@Override
			public void driverLoadFailuerEvent() {
				System.out.println("!driverLoadFailuerEvent!");
			}
			
			@Override
			public void disconnectEvent() {
				System.out.println("!disconnectEvent!");
			}
			
			@Override
			public void connectEvent() {
				System.out.println("!connectEvent!");
			}

			@Override
			public void operationFailureBusConnecting() {
				System.out.println("!operationFailureBusConnecting!");
			}
		});
	}
}
