package pl.mazon.jatmega.core.command;

import pl.mazon.jatmega.core.address.Address16;
import pl.mazon.jatmega.core.model.MetaModel;

/**
 * 
 * @author radomir.mazon
 *
 */
public abstract class Memory16Command implements ICommand<MetaModel, MetaModel> {
	
	private MetaModel request = new MetaModel();
	private MetaModel response = new MetaModel();
	private static final byte OPERATION_GET = 4;
	private static final byte OPERATION_SET = 3;
	private byte operation;
	
	public interface Memory16Get {
		void onSuccess(int value);
	}
	
	public Memory16Command(Address16 addr16) {
		request.add(addr16.byteValue());
		operation = OPERATION_GET;
	}
	public Memory16Command(Address16 addr16, int value) {
		request.add(addr16.byteValue());
		request.add((byte)(value & 0x00FF));
		request.add((byte)((value >> 8) & 0x00FF));
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