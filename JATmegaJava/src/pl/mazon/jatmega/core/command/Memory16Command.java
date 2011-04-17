package pl.mazon.jatmega.core.command;

import pl.mazon.jatmega.core.address.Address16;
import pl.mazon.jatmega.core.model.WordModel;

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
public abstract class Memory16Command implements ICommand<WordModel, WordModel> {
	
	private WordModel request;
	
	public static final int AND = 'A';
	public static final int OR = 'O';
	public static final int SET = 'S';
	
	public Memory16Command(int operation, Address16 addr16, int value) {
		request = new WordModel();
		request.add(addr16.intValue());
		request.add(value);
		request.add(operation);
	}
	
	@Override
	public char getTargetName() {
		return 'M';
	}
	
	@Override
	public WordModel getRequest() {
		return request;
	}
	
	@Override
	public WordModel getResponse() {
		return request;
	}
}