package pl.mazon.jatmega.logger;


/**
 * Fasada loggera 
 * W zależności czy jest to java czy android można zaimplementować odpowiednią klasą.
 * @author radomir.mazon
 *
 */

public interface Logger {
	void setClazz(Class clazz);
	void info(String info);
	void error(String error);
	void debug(String debug);
}
