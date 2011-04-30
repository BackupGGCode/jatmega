package pl.mazon.jatmega.logger;

public class NullLogger implements Logger {

	@Override
	public void info(String info) {		
	}

	@Override
	public void error(String error) {
	}

	@Override
	public void debug(String debug) {	
	}

	@Override
	public void setClazz(Class clazz) {
	}

}
