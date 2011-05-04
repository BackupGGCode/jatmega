package pl.mazon.jatmega.core.bus.config;

import pl.mazon.jatmega.core.bus.IBusConfig;

/**
 * 
 * @author radomir.mazon
 *
 */

public class SocketBusConfig implements IBusConfig {

	private String remoteDeviceAddress;
	
	public SocketBusConfig(String remoteDeviceAddress) {
		this.remoteDeviceAddress = remoteDeviceAddress;
	}
	
	@Override
	public String getClassName() {
		return "pl.mazon.jatmega.core.bus.SocketBus";
	}

	public String getRemoteDeviceAddress() {
		return remoteDeviceAddress;
	}
}