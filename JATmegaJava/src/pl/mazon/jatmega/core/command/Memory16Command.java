package pl.mazon.jatmega.core.command;

import pl.mazon.jatmega.core.address.Address16;
import pl.mazon.jatmega.core.model.MetaModel;

/**
 * 
 * @author radomir.mazon
 *
 */
public abstract class Memory16Command implements ICommand<MetaModel, MetaModel> {
	
	private MetaModel request;
	
	public Memory16Command(Address16 addr16) {
		request = new MetaModel();
		request.add(addr16.byteValue());
	}
	public Memory16Command(Address16 addr16, int value) {
		request = new MetaModel();
		request.add(addr16.byteValue());
		request.add((byte)((value >> 8) & 0x00FF));
		request.add((byte)(value & 0x00FF));
	}
	
	@Override
	public boolean isResponseMendatory() {
		return true;
	}
	
	@Override
	public byte getTargetName() {
		return 2;
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