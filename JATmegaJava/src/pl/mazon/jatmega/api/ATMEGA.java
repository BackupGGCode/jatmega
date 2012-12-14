package pl.mazon.jatmega.api;

import pl.mazon.jatmega.core.BusManager;
import pl.mazon.jatmega.core.ProtocolManager;
import pl.mazon.jatmega.core.address.Address16;
import pl.mazon.jatmega.core.address.Address8;
import pl.mazon.jatmega.core.bus.IBus;
import pl.mazon.jatmega.core.command.Memory16Command;
import pl.mazon.jatmega.core.command.Memory16Command.Memory16Get;
import pl.mazon.jatmega.core.command.Memory8Command;
import pl.mazon.jatmega.core.command.TestCommand;
import pl.mazon.jatmega.core.model.MetaModel;
import pl.mazon.jatmega.logger.LogFactory;
import pl.mazon.jatmega.logger.Logger;

/**
 * 
 * @author radomir.mazon
 *
 */

public class ATMEGA {

	private static final Logger logger = LogFactory.getLog(ATMEGA.class);
	
	private ProtocolManager protocolManager;
	
	private IBus bus; 
	
	public ATMEGA() {
		this.protocolManager = ProtocolManager.getInstance();
		this.bus = BusManager.getInstance().getBus();
		protocolManager.setBus(bus);
		bus.connect();
		//test
		protocolManager.apply(new TestCommand((byte)13, (byte)7) {
			
			@Override
			public void onSuccess(MetaModel response) {
				if (response.getOperandB() != 13+7) {
					logger.error("Test failure");
				}
			}
			
			@Override
			public void onFailure() {
				logger.error("Connection failure");
			}
		});
		
		//version veryfication
		//todo...
	}
	
	public void set(Address8 _addr8, int _value) {
		final byte value = (byte)_value;
		final Address8 addr8 = _addr8;
		protocolManager.apply(new Memory8Command(addr8, value) {
			
			@Override
			public void onSuccess(MetaModel response) {
			}
			
			@Override
			public void onFailure() {
			}
		});
	}
	
	public void set(Address16 _addr16, int _value) {
		final Address16 addr16 = _addr16;
		final int value = _value;
		protocolManager.apply(new Memory16Command(addr16, value) {

			@Override
			public void onSuccess(MetaModel response) {
			}

			@Override
			public void onFailure() {
			}
		});
	}
	
	public void get(Address16 _addr16, final Memory16Get callback) {
		final Address16 addr16 = _addr16;
		protocolManager.apply(new Memory16Command(addr16) {
			
			@Override
			public void onSuccess(MetaModel response) {
				int result = response.getOperandA() & 0x000000ff;
				
				callback.onSuccess(result | (response.getOperandB() << 8));
			}
			
			@Override
			public void onFailure() {	
			}
		});
	}
}