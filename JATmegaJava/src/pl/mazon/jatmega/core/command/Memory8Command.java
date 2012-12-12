package pl.mazon.jatmega.core.command;

import pl.mazon.jatmega.core.address.Address8;
import pl.mazon.jatmega.core.model.MetaModel;

/**
 * 
 * @author radomir.mazon
 *
 */
public abstract class Memory8Command implements ICommand<MetaModel, MetaModel> {
	
	private MetaModel request;
	
	/**
	 * Operacja GET MEMORY
	 * Ta operacja wymaga odpowiedzi
	 */
	public Memory8Command(Address8 addr8) {
		request = new MetaModel();
		request.add(addr8.byteValue());
	}
	
	/**
	 * Operacja SET MEMORY
	 * Ta operacja nie wymaga odpowiedzi
	 */
	public Memory8Command(Address8 addr8, byte value) {
		request = new MetaModel();
		request.add(addr8.byteValue());
		request.add(value);
	}
	
	@Override
	public boolean isResponseMendatory() {
		return true;
	}
	
	@Override
	public byte getTargetName() {
		return 1;
	}
	
	@Override
	public MetaModel getRequest() {
		return request;
	}
	
	@Override
	public MetaModel getResponse() {
		return request;
	}
}