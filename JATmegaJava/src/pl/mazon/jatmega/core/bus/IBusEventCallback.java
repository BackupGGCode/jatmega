package pl.mazon.jatmega.core.bus;


/**
 * 
 * @author radomir.mazon
 *
 */

public interface IBusEventCallback {
	
	/**
	 * Następiło połączenie
	 */
	void connectEvent();

	/**
	 * Nastąpiło rozłączenie
	 */
	void disconnectEvent();
	
	/**
	 * Nie można załadować, zainicjować sterownika.
	 * Nastąpił wyjątek podczas inicjowania połączenia.
	 * Niemożliwe jest dalsze kontynuowanie działąnia.
	 */
	void driverLoadFailuerEvent();

	/**
	 * Trwa inicjowanie połączenia, lub ponowne łączenie.
	 * Sterownik jest zajęty. Dane, które miały być wysłane 
	 * zostaną utracone.
	 */
	void operationFailureBusConnecting();
}
