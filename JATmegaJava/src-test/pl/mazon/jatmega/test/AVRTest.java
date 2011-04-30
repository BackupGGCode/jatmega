package pl.mazon.jatmega.test;

import pl.mazon.jatmega.api.ATMEGAFactory;
import pl.mazon.jatmega.api.uC.ATMEGA168;

public class AVRTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		AVRTest test = new AVRTest();
		test.test();
	}
	
	public synchronized void test() throws InterruptedException {
		ATMEGA168 atmega = (ATMEGA168) ATMEGAFactory.getATMEGA(ATMEGAFactory.ATMEGA168);
		atmega.set(atmega.io.DDRB, 0xff);
		for (int i=0; i< 100; i++) {
			wait(100);
			atmega.set(atmega.io.PORTB, 0xAA);
			wait(100);
			atmega.set(atmega.io.PORTB, 0x55);
		}		
	}

}
