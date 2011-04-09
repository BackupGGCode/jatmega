package pl.mazon.jatmega.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mazon.jatmega.core.BusManager;
import pl.mazon.jatmega.core.ProtocolManager;
import pl.mazon.jatmega.core.bus.IBus;
import pl.mazon.jatmega.core.bus.IBusEventCallback;
import pl.mazon.jatmega.core.command.TestCommand;
import pl.mazon.jatmega.core.model.IntegerModel;

/**
 * 
 * @author radomir.mazon
 *
 */

public class Main {
	
	private static final Log logger = LogFactory.getLog(Main.class);
	
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
				//fatal :(
			}
			
			@Override
			public void onSuccess(IntegerModel response) {
				logger.info("wynik = " + response.getC());
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
