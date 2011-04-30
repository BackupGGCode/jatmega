package pl.mazon.jatmega.logger;

public class LogFactory {

	@SuppressWarnings("rawtypes")
	static public Logger getLog(final Class clazz) {
		Logger logger = null;
		try {
			logger = (Logger) Class.forName("pl.mazon.jatmega.logger.Log4JFasade").newInstance();
			logger.setClazz(clazz);
		} catch (final Exception e) {
			logger = null;
		}
		if (logger == null) {
			try {
				logger = (Logger) Class.forName("pl.mazon.jatmega.logger.AndroidLoggerFasade").newInstance();
				logger.setClazz(clazz);
			} catch (final Exception e) {
				logger = null;
			}
		}
		if (logger == null) {
			logger = new NullLogger();
		}
		return logger;
	}
}
