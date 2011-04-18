package pl.mazon.jatmega.core.command;

import pl.mazon.jatmega.core.address.Address8;
import pl.mazon.jatmega.core.model.ByteModel;

/**
 * 
 * @author radomir.mazon
 * 
 *     bajt		opis
 *     0		Adres RAM
 *     1		Wartosc
 *     2		Operacja
 *
 *     rodzaj operacji:
 *     S		=
 *     A		AND
 *     O		OR
 *
 */
public abstract class Memory8Command implements ICommand<ByteModel, ByteModel> {
	
	private ByteModel request;
	
	public static final int AND = 'A';
	public static final int OR = 'O';
	public static final int SET = 'S';
	
	public Memory8Command(int operation, Address8 addr8, int value) {
		request = new ByteModel();
		request.add(addr8.intValue());
		request.add(value);
		request.add(operation);
	}
	
	@Override
	public char getTargetName() {
		return 'M';
	}
	
	@Override
	public ByteModel getRequest() {
		return request;
	}
	
	@Override
	public ByteModel getResponse() {
		return request;
	}
}