package pl.mazon.jatmega.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mazon.jatmega.core.BusManager;
import pl.mazon.jatmega.core.ProtocolManager;
import pl.mazon.jatmega.core.bus.IBus;
import pl.mazon.jatmega.core.bus.IBusEventCallback;
import pl.mazon.jatmega.core.command.TestCommand;

/**
 * 
 * @author radomir.mazon
 *
 */

public class Main {
	
	private Log logger = LogFactory.getLog(Main.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ProtocolManager protocolManager = ProtocolManager.getInstance();
		
		IBus bus = BusManager.getInstance().getBus();

		//menadzer protokolu musi miec przez co sie komunikowac.
		protocolManager.setBus(bus);
		
		
		//test komunikacji 
		//TestProtocol zwraca sumę liczb
		protocolManager.apply(new TestCommand(13, 7) {
			
			@Override
			public void onFailure() {
				int i=0; i++;
			}
			
			@Override
			public void onSuccess() {
				int wynik = getResponse();
				wynik++;
			}
		});
		
		
		//test powiadomień z Bus
		bus.addEventCallback(new IBusEventCallback() {
			
			@Override
			public void driverLoadFailuerEvent() {
				int i=0; i++;
			}
			
			@Override
			public void disconnectEvent() {
				int i=0; i++;
			}
			
			@Override
			public void connectEvent() {
				int i=0; i++;
			}
		});
	}
}
