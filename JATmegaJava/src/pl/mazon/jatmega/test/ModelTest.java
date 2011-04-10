package pl.mazon.jatmega.test;

import pl.mazon.jatmega.core.model.ByteModel;


public class ModelTest {

	public static void main(String[] args) {
		ByteModel model1 = new ByteModel();
		ByteModel model2 = new ByteModel();
		ByteModel model3 = new ByteModel();
		model1.fromString("A4");
		model2.fromString("A434");
		model3.fromString("A434F1");
		
		String m1 = model1.toString();
		String m2 = model2.toString();
		String m3 = model3.toString();
		
		model3.set(1, 0x04);
		model3.set(2, 0x05);
		
		String a = model1.toString();
		int i=0;i++;
	}
	
}
