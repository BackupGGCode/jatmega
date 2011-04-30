package pl.mazon.jatmega.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Log4JFasade implements Logger {

	private Log logger;
	
	@SuppressWarnings("rawtypes")
	public void setClazz(Class clazz) {
		logger = LogFactory.getLog(clazz);
	}
	
	@Override
	public void info(String info) {
		if (logger != null) {
			logger.info(info);
		}
	}

	@Override
	public void error(String error) {
		if (logger != null) {
			logger.error(error);
		}
	}

	@Override
	public void debug(String debug) {
		if (logger != null) {
			logger.debug(debug);
		}
	}

}
