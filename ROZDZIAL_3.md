Kolejnym krokiem jest zbudowanie mechanizmu pozwalającego wywołać zdalną procedurę z Javy na AVR. Są dwa sposoby wywołań takich procedur. Synchroniczne i asynchroniczne.

  * Synchroniczne to takie, które po wywołaniu czekają na zakończenie całej procedury i dopiero wtedy kończą swoje działania. Tutaj należy zastanowić się czy nie ograniczyć czasu wykonania takiej procedury do pewnej wartości i po jej upłynięciu kończyć procedurę z błędem (timeout).

  * Asynchroniczne wykonanie procedury bazuje na mechanizmie callback. Wywołując procedurę podajemy dane wejściowe, oraz przekazujemy obiekt, który obsługuje wynik z procedury. Dodatkowo może obsługiwać również błędy (timeout).

W projekcie wykorzystujemy mechanizm asynchroniczny.

# Niskopoziomowe użycie zdalnego wywołania #
### Strona JAVA ###
Portem komunikacyjnym jest komenda (pl.mazon.jatmega.core.command). Interfejsem komend jest ICommand.
```
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
```

Aby wykonać daną komendę należy przekazać obiekt komendy metodzie apply() obiektowu ProtocolManager.

```
		//TestProtocol zwraca sumę liczb
		protocolManager.apply(new TestCommand(13, 7) {
			
			@Override
			public void onFailure() {
				//fatal :(
			}
			
			@Override
			public void onSuccess(IntegerModel response) {
				logger.info("wynik = " + response.getC());
			}
		});
```

Konstruktor komendy jest odpowiedzialny za wykonanie wszystkich przygotowań do działąnia komendy. Przygotowanie pytania (request), dlatego najlepiej wejściem komendy są parametry konstruktora, a wyjściem są parametry metody onSuccess().
Komenda musi implementować metody getRequest() i getResponse(). Mogą to być różne obiekty IModel, może być to również ten sam obiekt.

### Odpowiednik po stronie AVR: ###

Komendy dziedziczą po klasie ICommand (/command).

```
class ICommand {
public:
	virtual void apply(char* request, char* response)=0;
	virtual char getTargetName()=0;
};
```

Aby możliwe było sparowanie komend po stronie AVR i JAVA implementacje ICommand muszą zwrócić taki sam znak metodą getTargetName();

Wykonanie komendy może odbyć się w następujący sposoby:
1. W specyficznych sytuacjach, kiedy wykonanie komendy zawiera bardzo niewielką liczbę cykli zegara, może odbyć się bezpośrednio w metodzie apply().
(Tak jak w przypadku TestCommand):
```

void TestCommand::apply(char* request, char* response) {
	integerModel.fromString(request);
	integerModel.add(
			integerModel.get(0) + integerModel.get(1)
			);
	integerModel.toString(response);
}

```