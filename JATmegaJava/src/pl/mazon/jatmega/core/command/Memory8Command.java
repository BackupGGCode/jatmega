package pl.mazon.jatmega.core.command;

import pl.mazon.jatmega.core.address.Address8;
import pl.mazon.jatmega.core.model.MetaModel;

/**
 * 
 * @author radomir.mazon
 *
 */
public abstract class Memory8Command implements ICommand<MetaModel, MetaModel> {
	
	private MetaModel request = new MetaModel();
	private MetaModel response = new MetaModel();
	private static final byte OPERATION_GET = 2;
	private static final byte OPERATION_SET = 1;
	private byte operation;
	
	/**
	 * Operacja GET MEMORY
	 * Ta operacja wymaga odpowiedzi
	 */
	public Memory8Command(Address8 addr8) {
		request = new MetaModel();
		request.add(addr8.byteValue());
		operation = OPERATION_GET;
	}
	
	/**
	 * Operacja SET MEMORY
	 * Ta operacja nie wymaga odpowiedzi
	 */
	public Memory8Command(Address8 addr8, byte value) {
		request = new MetaModel();
		request.add(addr8.byteValue());
		request.add(value);
		operation = OPERATION_SET;
	}
	
	@Override
	public boolean isResponseMendatory() {
		if (operation == OPERATION_GET) {
			return true;
		}
		return false;
	}
	
	@Override
	public byte getTargetName() {
		return operation;
	}
	
	@Override
	public MetaModel getRequest() {
		return request;
	}
	
	@Override
	public MetaModel getResponse() {
		return response;
	}
}