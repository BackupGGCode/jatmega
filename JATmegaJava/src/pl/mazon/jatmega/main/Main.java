package pl.mazon.jatmega.main;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pl.mazon.jatmega.core.BusManager;
import pl.mazon.jatmega.core.bus.IBus;

/**
 * 
 * @author radomir.mazon
 *
 */

public class Main {
	
	private Log logger = LogFactory.getLog(Main.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IBus bus = BusManager.getInstance().getBus();

		
	}
}
