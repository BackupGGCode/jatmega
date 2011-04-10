package pl.mazon.jatmega.core.command;

import pl.mazon.jatmega.core.model.IModel;
import pl.mazon.jatmega.core.model.ByteModel;

/**
 * Test polega na przeliczeniu wartości
 * a i b. Wynik w odpowiedzi z atmega zawieta c=a+b
 * @author radomir.mazon
 */

public abstract class TestCommand implements ICommand<ByteModel, ByteModel> {

	private ByteModel request;
	
	private ByteModel response;
	
	public TestCommand(int a, int b) {
		request = new ByteModel();
		response = new ByteModel();
		request.add(a);
		request.add(b);
	}
	
	@Override
	public ByteModel getResponse() {
		return response;
	}
	
	@Override
	public ByteModel getRequest() {
		return request;
	}

	@Override
	public char getTargetName() {
		return 'X';
	}
}
