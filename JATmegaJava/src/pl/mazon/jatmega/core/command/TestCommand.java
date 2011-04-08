package pl.mazon.jatmega.core.command;

import pl.mazon.jatmega.core.model.IntegerModel;

/**
 * Test polega na przeliczeniu wartości
 * a i b. Wynik w odpowiedzi z atmega zawieta c=a+b
 * @author radomir.mazon
 */

public abstract class TestCommand implements ICommand {

	private IntegerModel request;
	
	private IntegerModel response;
	
	public TestCommand(int a, int b) {
		request = new IntegerModel();
		request.setA(a);
		request.setB(b);
	}
	
	public int getResponse() {
		return response.getA();
	}
	
	@Override
	public String serialize() {
		return request.toString();
	}

	@Override
	public void deserialize(String message) {
		response = new IntegerModel();
		response.fromString(message);
	}
	
	@Override
	public char getTargetName() {
		return 'X';
	}
}
