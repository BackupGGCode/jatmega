/*
 * ProtocolContriller.h
 *
 *  Created on: 12-03-2011
 *      Author: radomir.mazon
 */

#ifndef PROTOCOLCONTROLLER_H_
#define PROTOCOLCONTROLLER_H_

#include "saf2core.h"
#include "Error.h"
/*
 *	Radomir Mazon
 *	2012-11-29
 *
 *Ramka wiadomosci
 * byte		opis
 * 	  0		0xF0 - operacja, 0x0F - suma kontrolna
 * 	  1		operand 0
 * 	  ...
 * 	  n		operand n
 * 	  n+1	EOM
 *
 * 	  gdzie n = COMMAND_BUFFER_SIZE-1
 */

/**
 * Uwaga:
 * Dane przesylane przez RS sa binarne, moga zatem przyjmowac wartosci od 0 do 255
 * Jednak jedna z wartosci jest szczegolna i oznacza koniec wiadomosci EOM (End of Message)
 * Poniewaz dlugosc przesylanej wiadomosci moze byc zmienny od 1 do 4 bajtow na EOM moze
 * wystapic na pozycji 1,2,3 lub 4. Nie ma mozliwosci odroznienia czy na danej pozycji wystapil EOM
 * czy miala byc to rzeczywiscie taka wartosc i nie jest to koniec wiadomosci (ramki)
 * Problem ten zostal rozwiazany nastepujaco.
 * W sytuacji kiedy serwer ma wyslac wartosc EOM, ktora nie oznacza konca wiadomosci tylko cyfre 0x13,
 * wysyla on 2 bajty PEOM i EOM. Taki zestaw jest tlumaczony na wartosc operandu 0x13. Poniewaz zostala
 * uzyta do tego celu wartosc 0x12, ona rowniez jest poprzedzona prefiksem PEOM i przyjmuje wartosc dwuch
 * bajtow PEOM i PEOM co jest tlumaczone na wartosc 0x12 operandu.
 *
 */

/**
 * Pytania i odpowiedzi
 * Kazda wiadomosc odbierana i wysylana ma postac ramki. Wiadomosci przychodzace nazywane sa pytaniami (requestBuffer),
 * wiadomosci wychodzace nazywane sa odpowiedziami(responseBuffer). Mozliwe jest wystapowanie komunikacji pytanie-odpowiedz,
 * pytanie bez odpowiedz oraz sama odpowiedz. Dzieki takiemu rozwiazaniu mozliwe jest inicjowanie komunikacji przez obie
 * strony i wymiana ich dowolna ilosc razy, podczas jednej konwersacji. Rodzaj zastosowanego modelu kominikacji jest zaimplementowany
 * w danym command.
 */

/**
 * Obsluga pytania z serwera (odbiorcy)
 * Strumien wejsciowy jest zapisywany w wewnetrznym buforze commandBuffor, gdzie wiadomosc jest skladana az do napotkania znaku EOM.
 * Po zakonczeniu odbierania wiadomosci pierwszy bajt ramki jest dokodowany w celu powiadomienia odpowiedniej implementacji command,
 * pozostaje bajty sa zapisywane w requestBuffer w tablicy operand 0...n. W polu count zapisana jest wielkosc tablicy (poniewaz odebrana
 * ramka moze miec zmienna wielkosc, jednak nie wiecej niz n (COMMAND_BUFFER_SIZE-1)
 * Protocol Controller powiadamia odpowiednia command wysylajac jedno ze zdarzen COMMAND_ (z pliku Event.h), w polu value zdarzenia
 * przesylany jest index w tablicy globalnej requestBuffer.
 */

/**
 *	Obsluga odpowiedzi do serwera
 *  Command po odebraniu zdarzenia COMMAND_ (plik Event.h) może zakonczyc swoje dzialanie, lub przygotowac odpowiedz
 *  w globalnej tablicy responseBuffer o indeksie przekazanym w polu value obslugi eventu. Wykonyje to wysylajac zdarzenie
 *  RESPONSE_ (plik Event.h)
 *  Mozliwe jest wyslanie odpowiedzi zainicjowanej nie obsluga pytania z serwera, ale programowego wywolania.
 *  W tym celu konieczne jest zarezerwowanie w buforze response miejsca na wiadomosc odpowiedzi nalezy wywolac
 *  metoge pc_getMessageIndex() (message poniewaz index ten jest jednoczesnie indexem dla requestBuffer i responseBuffer
 */

/**
 * Konwencja przydzielania numerow COMMAND_ i RESPONSE_ (Event.h).
 * W deklaracjach eventow na komunikacje z command zarezerwowana jest pula numerow od 0xE0 do 0xFF.
 * gdzie numery 0xE0 - 0xEF - sa numerami odpowiedzi command (max 16 command)
 * 	     numery 0xF0 - 0xFF - sa numerami pytan command
 * Protocol Controller nasluchuje wszystkich zdarzen z puli 0xE0 - 0xEF, po odebraniu zdarzenia wysyla odpowiedz do serwera.
 * UWAGA: obecna wersja ma mozliwosc zaadresowania 16 roznym chmend do wykonania.
 */

#define COMMAND_BUFFER_SIZE 4
#define MESSAGE_BUFFER_SIZE 4

typedef struct {
	//tablica operandow
	uint8_t operand[COMMAND_BUFFER_SIZE-1];
	uint8_t count;
} Message_t;

#define EOM 0x13
#define PEOM 0x12

/**
 * Obsluga RS232
 * Odbiera znak i zapisuje go w commandBuffer, po odebraniu znaku nowej linii
 * Pasuje ramke wiadomosci i tworzy wiadomosc w messagebuffer
 */
void pc_onEnevt(saf_Event event);

/**
 * Zostala odebrana pelna wiadomosc (linia), nalezy wywolac event obslugi
 * odebranego polecenia i przekazac mu odebrane operancy (A i B). Obdywa sie to
 * za podrednictwem globalnej zmienne requestBuffer
 */
void _pc_applyCommand();

/**
 * Po wykonaniu command, moze nastapic koniecznosc odpowiedzi do nadawcy (dopuszcza
 * sie tez inicjace samej odpowiedzi, bez odebrania requesta. Nalezy wtedy spreparowac
 * odpowiedni event, lub bezposrednio wywolac protocol controller za pomoca EVENT_PC_RESPONSE
 */
void _pc_applyResponse(uint8_t code, int value);

/**
 * Rezerwuje wolne miejsce w buforach do obslugi wiadomosci po za Protocol Controller
 */
uint8_t pc_getMessageIndex();

#endif /* PROTOCOLCONTROLLER_H_ */

