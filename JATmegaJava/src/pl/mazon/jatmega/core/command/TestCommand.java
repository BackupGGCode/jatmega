package pl.mazon.jatmega.core.command;

import pl.mazon.jatmega.core.model.MetaModel;


/**
 * Test polega na przeliczeniu wartości
 * a i b. Wynik w odpowiedzi z atmega zawieta c=a+b
 * lub a, b i c jesli wyslemy trzeci operand c=a+b+c
 * @author radomir.mazon
 */

public abstract class TestCommand implements ICommand<MetaModel, MetaModel> {

	private MetaModel request;
	
	private MetaModel response;
	
	public TestCommand(byte a, byte b, byte c) {
		request = new MetaModel();
		response = new MetaModel();
		request.add(a);
		request.add(b);
		request.add(c);
	}
	
	public TestCommand(byte a, byte b) {
		request = new MetaModel();
		response = new MetaModel();
		request.add(a);
		request.add(b);
	}
	
	@Override
	public MetaModel getResponse() {
		return response;
	}
	
	@Override
	public MetaModel getRequest() {
		return request;
	}

	@Override
	public byte getTargetName() {
		return (byte)0x00;
	}

	@Override
	public boolean isResponseMendatory() {
		return true;
	}
}
