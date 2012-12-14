package pl.mazon.jatmega.core.bus;

import java.util.Properties;

/**
 * 
 * @author rmazon
 *
 */

public class RSBusConfig implements IBusConfig {
	
	private String name;
	
	private String driverName;
	
	private int baudRate;
	
	private int dataBits;
	
	private int stopBits;
	
	private int pariti;
	
	public String getName() {
		return name;
	}
	
	public String getDriverName() {
		return driverName;
	}
	
	public int getBaudRate() {
		return baudRate;
	}
	
	public int getDataBits() {
		return dataBits;
	}
	
	public int getStopBits() {
		return stopBits;
	}
	
	public int getPariti() {
		return pariti;
	}
	
	@Override
	public String getClassName() {
		return "pl.mazon.jatmega.core.bus.RSBus";
	}

	@Override
	public void init(Properties properties, String osName) {
		this.name = properties.getProperty(osName+"port");
		this.driverName = properties.getProperty(osName+"driver");
		this.baudRate = new Integer(properties.getProperty("baudRate"));
		this.dataBits = 8;
		this.stopBits = 1;
		this.pariti = 0;
	}
	
}
