package pl.mazon.jatmega.core.command;

import pl.mazon.jatmega.core.model.IModel;

/**
 * 
 * @author radomir.mazon
 *
 */

public interface ICommand<REQUEST extends IModel, RESPONSE extends IModel> {
		
	/**
	 * Paruje komende po stwonie AVR i JAVA
	 * przyjmuje w obecnej wersji 2.0 wartosci z przedzialu 0-F
	 */
	byte getTargetName();
	
	/**
	 * Pobiera obiekt modelu, który będzie stosowany podczas komunikacji
	 */
	REQUEST getRequest();
	RESPONSE getResponse();
	
	/**
	 * @return 
	 * Nie wszystkie command wymagaja odpowiedzi. Dla tych które nie wymagaja odpowiedzi metoda
	 * zwraca true. 
	 * Jest to istotne podczas kontroli obecnosci odbiorcy. We wczesniejszej wersji kazda wiadomosc
	 * powiadale unikalny numer sygnatury. Wszystkie wystalen wiadomosci musialy posiadac taki numer
	 * co dodatkowo wydluzylo ilosc przesylanych danych. Po wyslaniu wiadomosci sygnatura i numer
	 * command byl zapisywany w specjalnej macie z informacja o czasie jego wystalania, jesli czas 
	 * od wyslania przekraczal ustalony TIMEOUT wiadomosc byla uznawana za zagubiona, i mozna bylo 
	 * wnioskowac ze odbiorca zniknal. 
	 * W nowej wersji 2.0 w celu redokcji informacji jakie sa przesylane do odbiorcy sygnatura ta nie
	 * jest wysylana. Dodatkowo nie wszystkie wiadomosci musza miec wiadomosc zwrotna. Czasami nie jest
	 * istotne czy wiadomosc dotarla (np. gdy ma ona bardzo krotki czas waznosci - polozenie serwomechanizu
	 * wysylane co 500ms, po tym czasie nie jest juz istotne czy ostatnia wiadomosc dotarla bo wysylamy juz 
	 * kolejna.)
	 * Jak jednak ocenic czy wiadomosc dotarla jest wymagana jest odpowiedz. (np. Ping). 
	 * W nowej wersji 2.0 zapisywana jest liczba wyslanych wiadomosci o danych typie jesli wymagana jest 
	 * odpowiedz. Taka informacja jest wystarczajaca, bo nie jest dla nas istotne czy ta konkretma wiasomosc
	 * dotarla do celu, ale czy dotarly wszystkie. Jesli otrzymamy wiadomosc od odbiorcy, to w pierwszej 
	 * kolejnosci usuwana jest z lisyt oczekujacych na odpowiedz ta ktora byla wyslana jako pierwsza.
	 * 
	 * dodano: Jednak nie mozna tak zrobic. Gdy z jakiegos powodu wiadomosc dostanie timeout, ale po nim 
	 * wiadomosc jednak dojdzie od nadawcy, zostanie blednie sparowana. 
	 * Zasotosowane zostanie inne rozwiazanie. 
	 * Komendy, ktore maja wymagana odpowiedz, zawieraja dodatkowy bajt ramki, bedacy pierwszym wolnym numerem
	 * danej komendy, ktra nie oczekuje na odpowiedz. Ten specjalny znacznik po stronie nadawcy jest 
	 * traktowany jako czesc ramki, jednak po stronie atmega jest on operandem 0. Nalezy o tym pamietac, 
	 * gdyz komenda po stronie atmega musi odeslac ten operand 0 o takiej samej wartosci do nadawcy. 
	 * Tylko wtedy zostanie poprawnie parowany z wiadomoscia wyslana.
	 */
	boolean isResponseMendatory();
	
	/**
	 * Callback
	 * @param response
	 */
	void onSuccess(RESPONSE response);
	void onFailure();
	
	/**
	 * UWAGA: komenda, która ma byc asynchroniczna (czyli taka która odbiorca moze powiadomic
	 * bez uprzedniego wyslania pytania, nalezy zarejestrowac w fabryce commend.
	 * CommandFactory.addCommand(this);
	 */
	
	
}
