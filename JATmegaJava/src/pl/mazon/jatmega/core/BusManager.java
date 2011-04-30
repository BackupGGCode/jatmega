package pl.mazon.jatmega.core;

import java.io.IOException;
import java.util.Properties;

import pl.mazon.jatmega.core.bus.IBus;
import pl.mazon.jatmega.core.bus.IBusConfig;
import pl.mazon.jatmega.core.bus.config.RSBusConfig;
import pl.mazon.jatmega.core.bus.config.SocketBusConfig;
import pl.mazon.jatmega.logger.LogFactory;
import pl.mazon.jatmega.logger.Logger;

/**
 * Zarządzanie magistralą
 * @author rmazon
 *
 */
public class BusManager {

	private static BusManager instance = new BusManager();
	
	private Logger logger = LogFactory.getLog(BusManager.class);
	
	public IBus getBus() {
		IBusConfig config = getConfig();
		return getBus(config);
	}
	
	public IBus getBus(IBusConfig config) {
		IBus bus = null;
		try {
			bus = (IBus) Class.forName(config.getClassName()).newInstance();
		} catch (Exception e) {
			logger.error("Bus " + config.getClassName() + " loaded error; driver: ");
			throw new IllegalStateException(e.getMessage());
		}
		logger.info("Bus " + config.getClassName() + " loaded success.");
		bus.init(config);
		return bus;
	}
	
	public IBusConfig getConfig() {
		logger.info("Loading configuration...");

		Properties properties = new Properties(); 
		try {
			properties.load(this.getClass().getResourceAsStream("/config.properties")); 
			} 
		catch (IOException e) 
		{
			logger.error("Can't load config file... ");
			throw new IllegalStateException(e);
		} 
		return getConfig(properties);
	}
	
	private IBusConfig getConfig(Properties properties) {
		String interfacee = properties.getProperty("interface");
		IBusConfig config = null;
		
		String os = System.getProperty("os.name");
		String osPrefix = "";
		if (os.toLowerCase().startsWith("windows")) {
			osPrefix="windows.";
		} else if (os.toLowerCase().startsWith("linux")) {
			osPrefix="linux.";
		} else {
			logger.error("Unknow OS.");
			throw new RuntimeException();
		}
		
		
		if (interfacee.equals("RS232")) {
			Integer baudRate = new Integer(properties.getProperty("baudRate"));
			String portName = properties.getProperty(osPrefix+"port");
			String driverName = properties.getProperty(osPrefix+"driver");			
			config = new RSBusConfig( 
					portName, 
					driverName, 
					baudRate, 
					8, 
					1, 
					0);
		} else if (interfacee.equals("AndroidBluetooth")) {
			config = new SocketBusConfig();
		}
		
		if (config == null) {
			logger.error("Unknow interface: " + interfacee);
			throw new RuntimeException();
		}
		return config;
	}

	public static BusManager getInstance() {
		return instance;
	}
/*
	private synchronized boolean take() {
		if (!bus.isOnLine()) 
		{
			setChanged();
			notifyObservers(Boolean.FALSE);
			return false; 
		} else {
			setChanged();
			notifyObservers(Boolean.TRUE);
		}
		if (busy) {
			return false;
		} else {
			busy = true;
		}
		return true;
	}

	private synchronized void free() {
		busy = false;	
	}

	public synchronized boolean apply(Protocol protocol) {
		if (!take()) {
			return false;
		}
		protocol.setBus(bus);
		protocol.start();
		try {
			protocol.join(protocol.getTimeout());
		} catch (InterruptedException e) {
			logger.debug("Protocol " + protocol + " interrupt ");
		}
		if (protocol.isAlive()) {
			protocol.interrupt();
			logger.debug("Protocol " + protocol + " timeout.");
		}
		free();
		return true;
		
	}
	*/
}
