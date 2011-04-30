package pl.mazon.jatmega.core.bus.config;

import pl.mazon.jatmega.core.bus.IBusConfig;

/**
 * 
 * @author radomir.mazon
 *
 */

public class SocketBusConfig implements IBusConfig {

	
	public SocketBusConfig() {
	}
	
	@Override
	public String getClassName() {
		return "pl.mazon.jatmega.core.bus.SocketBus";
	}

}
