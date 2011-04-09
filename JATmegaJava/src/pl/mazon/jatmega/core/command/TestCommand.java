package pl.mazon.jatmega.core.command;

import pl.mazon.jatmega.core.model.IModel;
import pl.mazon.jatmega.core.model.IntegerModel;

/**
 * Test polega na przeliczeniu wartości
 * a i b. Wynik w odpowiedzi z atmega zawieta c=a+b
 * @author radomir.mazon
 */

public abstract class TestCommand implements ICommand<IntegerModel, IntegerModel> {

	private IntegerModel request;
	
	private IntegerModel response;
	
	public TestCommand(int a, int b) {
		request = new IntegerModel();
		response = new IntegerModel();
		request.setA(a);
		request.setB(b);
	}
	
	@Override
	public IntegerModel getResponse() {
		return response;
	}
	
	@Override
	public IntegerModel getRequest() {
		return request;
	}

	@Override
	public char getTargetName() {
		return 'X';
	}
}
