package pl.mazon.jatmega.core;

import java.io.IOException;
import java.util.Properties;

import pl.mazon.jatmega.core.bus.IBus;
import pl.mazon.jatmega.core.bus.IBusConfig;
import pl.mazon.jatmega.logger.LogFactory;
import pl.mazon.jatmega.logger.Logger;

/**
 * Zarządzanie magistralą
 * @author rmazon
 *
 */
public class BusManager {

	private static BusManager instance;
	
	private Logger logger = LogFactory.getLog(BusManager.class);
	
	private IBus bus;
	
	public IBus getBus() {
		if (bus == null) {
			IBusConfig config = getConfig();
			bus = getBus(config);
		}
		return bus; 
	}
	
	private IBusConfig getConfig() {
		logger.info("Looking for config...");
		Properties properties = new Properties(); 
		try {
			properties.load(this.getClass().getResourceAsStream("/config.properties")); 
			} 
		catch (IOException e) 
		{
			logger.error("Can't load config file... ");
			throw new IllegalStateException(e);
		} 
		String osPrefix = getOsPrefix(properties);
		String configClassName = properties.getProperty(osPrefix+"config");
		IBusConfig busConfig = null;
		try {
			busConfig = (IBusConfig) Class.forName(configClassName).newInstance();
		} catch (Exception e) {
			logger.error("Bus config" + configClassName + " loaded error.");
			throw new IllegalStateException(e.getMessage());
		}
		logger.info("Bus config" + configClassName + " loaded success.");

		busConfig.init(properties, osPrefix);
		
		return busConfig;
	}

	private String getOsPrefix(Properties properties) {
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
		return osPrefix;
	}

	public IBus getBus(IBusConfig config) {
		IBus bus = null;
		try {
			bus = (IBus) Class.forName(config.getClassName()).newInstance();
		} catch (Exception e) {
			logger.error("Bus " + config.getClassName() + " loaded error;");
			throw new IllegalStateException(e.getMessage());
		}
		logger.info("Bus " + config.getClassName() + " loaded success.");
		bus.init(config);
		return bus;
	}

	public static BusManager getInstance() {
		if (instance == null) {
			instance = new BusManager();
		}
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
