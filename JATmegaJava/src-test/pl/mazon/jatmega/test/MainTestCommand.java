package pl.mazon.jatmega.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mazon.jatmega.core.BusManager;
import pl.mazon.jatmega.core.ProtocolManager;
import pl.mazon.jatmega.core.bus.IBus;
import pl.mazon.jatmega.core.bus.IBusEventCallback;
import pl.mazon.jatmega.core.command.TestCommand;
import pl.mazon.jatmega.core.model.ByteModel;

/**
 * 
 * @author radomir.mazon
 *
 */

public class MainTestCommand {
	private static final Log logger = LogFactory.getLog(MainTestCommand.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ProtocolManager protocolManager = ProtocolManager.getInstance();
		
		IBus bus = BusManager.getInstance().getBus();
		
		//test powiadomień z Bus
		bus.addEventCallback(new IBusEventCallback() {
			
			@Override
			public void driverLoadFailuerEvent() {
				logger.info("Sterownik nie załadowany...");
			}
			
			@Override
			public void disconnectEvent() {
				logger.info("Rozłączony...");
			}
			
			@Override
			public void connectEvent() {
				logger.info("Połaczono...");
			}

			@Override
			public void operationFailureBusConnecting() {
				logger.info("Operation failure. Bus connecting...");
			}
		});
		
		//menadzer protokolu musi miec przez co sie komunikowac.
		protocolManager.setBus(bus);
		
		//test komunikacji 
		//TestProtocol zwraca sumę liczb
		protocolManager.apply(new TestCommand(13, 7) {
			
			@Override
			public void onFailure() {
				logger.error("fatal :(");
			}
			
			@Override
			public void onSuccess(ByteModel response) {
				logger.info("wynik = " + response.get(2));
			}
		});
		
		
		
	}
}
