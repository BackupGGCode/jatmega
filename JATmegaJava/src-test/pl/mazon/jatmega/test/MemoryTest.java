package pl.mazon.jatmega.test;

import pl.mazon.jatmega.api.ATMEGAFactory;
import pl.mazon.jatmega.api.uC.ATMEGA168;
import pl.mazon.jatmega.core.command.Memory16Command.Memory16Get;

public class MemoryTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		MemoryTest test = new MemoryTest();
		test.test();
	}
	
	public synchronized void test() throws InterruptedException {
		ATMEGA168 atmega = (ATMEGA168) ATMEGAFactory.getATMEGA(ATMEGAFactory.ATMEGA168);
		
		int[] testTab = {1,2,4,8,16,32,64,128,256,512,1024, 2048, 4096, 8192, 16384, 32768};
		
		
		for (int i=0; i<testTab.length; i++) {
			atmega.set(atmega.io.OCR1A, testTab[i]);

			atmega.get(atmega.io.OCR1A, new Memory16Get() {
				
				@Override
				public void onSuccess(int value) {
					System.out.println(value);
				}
			});
		}
		
	}

}
