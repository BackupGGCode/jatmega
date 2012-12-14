package pl.mazon.jatmega.core.bus;

import java.util.Properties;

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

	@Override
	public void init(Properties properties, String osName) {
		// TODO Auto-generated method stub
		
	}
}