package pl.mazon.jatmega.test;

import pl.mazon.jatmega.core.model.IntegerModel;


public class ModelTest {

	public static void main(String[] args) {
		IntegerModel model1 = new IntegerModel();
		IntegerModel model2 = new IntegerModel();
		IntegerModel model3 = new IntegerModel();
		model1.fromString("A4");
		model2.fromString("A4,34");
		model3.fromString("A4,34,F1");
		
		String m1 = model1.toString();
		String m2 = model2.toString();
		String m3 = model3.toString();
		
		model1.setB(4);
		model1.setC(5);
		
		String a = model1.toString();
		int i=0;i++;
	}
	
}
