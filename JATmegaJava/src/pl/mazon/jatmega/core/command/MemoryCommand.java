package pl.mazon.jatmega.core.command;

import pl.mazon.jatmega.core.model.IModel;

public abstract   class MemoryCommand implements ICommand {
	
	public MemoryCommand() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public char getTargetName() {
		return 'M';
	}
	
	@Override
	public IModel getRequest() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public IModel getResponse() {
		// TODO Auto-generated method stub
		return null;
	}

}