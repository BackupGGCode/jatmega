package pl.mazon.jatmega.test;

import pl.mazon.jatmega.api.ATMEGAFactory;
import pl.mazon.jatmega.api.uC.ATMEGA168;

public class ServoTest {

	private static final int speed = 10;
	private static final int min = 320-100;
	private static final int max = 480+100;
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		ServoTest test = new ServoTest();
		test.test();

	}
	
	synchronized void test() throws InterruptedException {
		ATMEGA168 atmega = (ATMEGA168) ATMEGAFactory.getATMEGA(ATMEGAFactory.ATMEGA168);
		//Configure TIMER1
		atmega.set(atmega.io.TCCR1A, (1<<atmega.io.COM1A1)|(1<<atmega.io.COM1B1)|(1<<atmega.io.WGM11));        //NON Inverted PWM
		atmega.set(atmega.io.TCCR1B, (1<<atmega.io.WGM13)|(1<<atmega.io.WGM12)|(1<<atmega.io.CS11)|(1<<atmega.io.CS10)); //PRESCALER=64 MODE 14(FAST PWM)

		atmega.set(atmega.io.ICR1, 4999);  //fPWM=50Hz (Period = 20ms Standard).

		atmega.set(atmega.io.DDRB, 0xFF);
		atmega.set(atmega.io.DDRD, 0xFF);
		while(true) {
		for (int i=min; i<max; i = i+3)
		{
				//atmega.set(atmega.io.OCR1A, 130);	//0 dgree min
			//atmega.set(atmega.io.OCR1A, 320);  //180 degree
			//wait(500);
				//atmega.set(atmega.io.OCR1A, 380);  //90
			atmega.set(atmega.io.OCR1A, i);  //90
			//wait(500);
			//atmega.set(atmega.io.OCR1A, 630);  //180 degree
			//atmega.set(atmega.io.OCR1A, 480);  //180 degree
			//OCR1A=650;  //Max
			//wait(500);
			//atmega.set(atmega.io.OCR1A, 380);  //180 degree
			wait(speed);
	   }
		for (int i=max; i>min; i = i-5)
		{
			atmega.set(atmega.io.OCR1A, i);  //90
			wait(speed);
	   }}

	}

}
