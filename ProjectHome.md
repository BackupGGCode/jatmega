### Jest już dostępna nowa wersja 2.0 ###

Wersja 2.0

  * Nowa jatmega jest oparta o SAF2.2. (http://code.google.com/p/simple-avr-framework/)
  * Jest napisana w języku C (obecna wersja jest w C++), dzięki czemu łatwiej jest ją adaptować do własnych projektów.
  * Zredukowana jest objętość zajmowana w pamięci programu (Program: 1858 bytes Data:        118 bytes) .
  * Zakres wykonywanych instrukcji ograniczy się do zapisu i odczytu pamięci SFR(podobnie jak to jest w poprzedniej wersji).
  * Uproszczona zostanie komunikacja aby zwiększyć jej wydajność... - Komendy mogą nie wymagać odpowiedzi, oraz jest zmienna długość przesyłanych wiadomości. Wiadomości przesyłane są w formacie binarnym, a nie jak w poprzedniej wersji w ASCII.

Wersja 2.1

  * traps - czyli mozliwosc ustawienia wartosci i maski dowolnej komorki sfr po ktorej zostanie wyslany komunikat.
  * initproc - w eeprom bedzie zapisana sekwencja inicjujaca. W przypadku awarii bedzie ona wykonywana. Po jej wykonaniu bedzie wysylany komunikat. W ten sposob kompuer bedzie wiedzial o gotowosci ukladu zdalnego. To tez zabezpieczy wymkniecie sie z podkontroli atmegi w przypasku awarii i restartu.


Projekt zawiera bibliotekę JAVA i kod C++ dla ATMEGA oraz sprzętowe rozwiązanie pozwalające wykorzystać uC jako końcówki wykonawczej dla programów pisanych w języku JAVA.

Projekt nie jest kompilatorem skrośnym JAVA -> C.
Projekt nie jest wersją JAVA dla uC AVR.

Fazy projektu:

  * Przygotowanie projektu pod kontem hardware. Wybór architektury, podzespołów.
  * Opracowanie niezawodnego protokołu półdupleks i sterowników sprzętowych.
  * Opracowanie architektury komend i interfejsów JATMEGA

Przykład zastosowania:

<a href='http://www.youtube.com/watch?feature=player_embedded&v=g3cBON5l1sw' target='_blank'><img src='http://img.youtube.com/vi/g3cBON5l1sw/0.jpg' width='425' height=344 /></a>


<a href='http://www.youtube.com/watch?feature=player_embedded&v=rj_pDUVG078' target='_blank'><img src='http://img.youtube.com/vi/rj_pDUVG078/0.jpg' width='425' height=344 /></a>

Podobne projekty:

http://code.google.com/p/simple-avr-framework/

http://code.google.com/p/avr-wav-interface/

http://code.google.com/p/xbee-api/