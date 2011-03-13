package pl.mazon.jatmega.core.bus;

/**
 * 
 * @author rmazon
 *
 */

public class RSBusConfig implements IBusConfig {

	private String interfacee;
	
	private String name;
	
	private String driverName;
	
	private int baudRate;
	
	private int dataBits;
	
	private int stopBits;
	
	private int pariti;
	
	public RSBusConfig(String interfacee, String portName, String driverName, int baudRate, int dataBits, int stopBits, int pariti) {
		this.interfacee = interfacee;
		this.name = portName;
		this.driverName = driverName;
		this.baudRate = baudRate;
		this.dataBits = dataBits;
		this.stopBits = stopBits;
		this.pariti = pariti;
	}
	
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
	public String getInterfacee() {
		return interfacee;
	}
	
}
