package pl.mazon.jatmega.core.command;

/**
 * 
 * @author radomir.mazon
 *
 */

public interface ICommand {
	void onSuccess();
	void onFailure();
	
	String serialize();
	void deserialize(String message);
	
	/**
	 * Paruje komende po stwonie AVR i JAVA
	 */
	char getTargetName();
}
