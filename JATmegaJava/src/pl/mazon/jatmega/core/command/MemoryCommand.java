package pl.mazon.jatmega.core.command;

public abstract   class MemoryCommand implements ICommand {

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deserialize(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public char getTargetName() {
		return 'M';
	}

}