package pl.mazon.jatmega.core.bus;

import java.util.Properties;

public interface IBusConfig {

	String getClassName();
	
	void init(Properties properties, String osName);
}
