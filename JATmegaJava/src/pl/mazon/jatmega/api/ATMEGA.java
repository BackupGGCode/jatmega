package pl.mazon.jatmega.api;

import pl.mazon.jatmega.core.BusManager;
import pl.mazon.jatmega.core.ProtocolManager;
import pl.mazon.jatmega.core.address.Address16;
import pl.mazon.jatmega.core.address.Address8;
import pl.mazon.jatmega.core.bus.IBus;
import pl.mazon.jatmega.core.command.Memory16Command;
import pl.mazon.jatmega.core.command.Memory8Command;
import pl.mazon.jatmega.core.command.TestCommand;
import pl.mazon.jatmega.core.model.ByteModel;
import pl.mazon.jatmega.core.model.WordModel;
import pl.mazon.jatmega.logger.LogFactory;
import pl.mazon.jatmega.logger.Logger;

public class ATMEGA {

	private static final Logger logger = LogFactory.getLog(ATMEGA.class);
	
	private ProtocolManager protocolManager;
	
	private IBus bus; 
	
	public ATMEGA() {
		this.protocolManager = ProtocolManager.getInstance();
		this.bus = BusManager.getInstance().getBus();
		protocolManager.setBus(bus);
		//test
		protocolManager.apply(new TestCommand(13,7) {
			
			@Override
			public void onSuccess(ByteModel response) {
				if ((13+7) == response.get(2)) {
					logger.info("Test ok");
				} else {
					logger.info("Test failure");
				}
				
			}
			
			@Override
			public void onFailure() {
				logger.error("Test aborted");
			}
		});
		
		//version veryfication
		//todo...
	}
	
	public void set(final Address8 addr8, final int value) {
		protocolManager.apply(new Memory8Command(Memory8Command.SET, addr8, value) {
			
			@Override
			public void onSuccess(ByteModel response) {
				if (!response.get(0).equals(new Integer(value))) {
					throw new IllegalStateException("Value set error: " + addr8.toString() + "," + value + "->" + response.get(0));
				}
			}
			
			@Override
			public void onFailure() {
				throw new IllegalStateException("Value not set: " + addr8.toString() + "," + value);
				
			}
		});
	}
	
	public void set(final Address16 addr16, final int value) {
		protocolManager.apply(new Memory16Command(Memory16Command.SET, addr16, value) {

			@Override
			public void onSuccess(WordModel response) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFailure() {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
}