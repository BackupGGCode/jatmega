package pl.mazon.jatmega.api;

import pl.mazon.jatmega.api.uC.ATMEGA168;

public class ATMEGAFactory {

	public static final String ATMEGA168 = "ATMEGA168";
	
	public static ATMEGA getATMEGA(String model) {
		if (model.equals(ATMEGA168)) {
			new ATMEGA168();
		}
		throw new IllegalStateException("Unknown uC " + model);
	}
}
