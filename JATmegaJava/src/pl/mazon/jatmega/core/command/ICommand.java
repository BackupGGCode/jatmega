package pl.mazon.jatmega.core.command;

import pl.mazon.jatmega.core.model.IModel;

/**
 * 
 * @author radomir.mazon
 *
 */

public interface ICommand<REQUEST extends IModel, RESPONSE extends IModel> {
	void onSuccess(RESPONSE response);
	void onFailure();
	
	/**
	 * Paruje komende po stwonie AVR i JAVA
	 */
	char getTargetName();
	
	/**
	 * Pobiera obiekt modelu, który będzie stosowany podczas komunikacji
	 */
	REQUEST getRequest();
	RESPONSE getResponse();
}
