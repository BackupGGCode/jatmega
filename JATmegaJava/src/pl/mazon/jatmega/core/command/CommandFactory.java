package pl.mazon.jatmega.core.command;


public class CommandFactory {

	@SuppressWarnings("rawtypes")
	public static ICommand getCommand(byte targetName) {
		
		/*if (targetName == (byte)0) {
			return new TestCommand();
		}*/
		
		return null;
	}
}
