#Opis dla osób, które dopiero rozpoczynają przygodę z AVR.

# Jak zacząć… #

## Hardware ##

Aby rozpocząć przygodę z AVR najlepiej zaopatrzyć się w gotową płytkę testową, do której podłączone są najczęściej używane peryferia, takie jak LCD, ośmiosegmentowe LED, mikrostyki itp. Na Allegro można znaleźć bardzo dużo autorskich rozwiązań, od prostych płytek z jednym LCD do kompletnych rozwiązań z dotykowym TFT LCD i kamerą na pokładzie. Najlepiej jednak odszukać prostą płytkę, która ma jedynie uC i interfejs RS232 lub USB do komputera. Taki zestaw nazywa się Arduino. Ostatnio pojawiły się nawet modele płyt z kolorowymi wyświetlaczami (z nokia 6100).

Oto moja płytka: http://mazon.pl/betoven/allegro/atmega/


Arduino lub jego klon zapewnia nam odpowiedni bootloader  na pokładzie procesora, oraz pewien spójny sposób rozmieszczenia wyprowadzeń uC. Oczywiście można zastosować inną płytkę testową, ważne jest jedynie to aby wiedzieć jaki bootloader jest wgrany i aby programowanie możliwe było za pośrednictwem portu RS232 (lub USB).

Na uwagę zasługuje również projekt mini płytki Boarduino (klon Arduino)  dostępnej na Allegro. Uważam, że jest to jedno z lepszych rozwiązań dla początkujących projektantów z uwagi na swoje małe wymiary i przystępną cenę. Do kompletnego zestawu wystarczy dokupić jedynie płytkę stykową widoczną na zdjęciu i kabelki.

![http://mazon.pl/betoven/allegro/atmega2/boarduino_2_wm.jpg](http://mazon.pl/betoven/allegro/atmega2/boarduino_2_wm.jpg)

http://allegro.pl/show_user_auctions.php?uid=7279156

## Software ##

Do programowania ATMEGA podobno jest najlepszy AVR STUDIO samego producenta tego uC. Jeśli jednak komuś nie zależy na debugowaniu jego pracy, to lepszym rozwiązaniem wydaje się Eclipse.

  1. Ściągamy projekt Eclipse w wersji  : Eclipse IDE for C/C++ Developers http://www.eclipse.org/downloads/packages/eclipse-ide-cc-developers/heliossr1
  1. Ściągamy i instalujemy WINAVR.  Oczywiście jest to środowisko dla Windows. (Jeśli ktoś zna instrukcję dla linux proszę o kontakt, chętnie dopiszę. Osobiście nie miałem okazji się tym zając) http://winavr.sourceforge.net/download.html
  1. Po uruchomieniu Eclipse, trzeba zainstalować odpowiednią wtyczkę dla AVR http://avr-eclipse.sourceforge.net/wiki/index.php/Plugin_Download
  1. Po instalacji wtyczki, Eclipse poprosi o restart. Konfiguracja przebiega automatycznie, pod warunkiem wcześniejszego zainstalowania WINAVR. Jeśli jednak systemem operacyjnym jest linux, to koniczne będzie ustawienie ręcznie ścieżek. Preferencje -> AVR -> Paths
  1. Aby możliwe było ściągnięcie projektu JATMEGA, konieczne jest zainstalowanie wtyczki SVN.
Help -> MarketPace -> EclipseMaketPlace; w polu Search: SVN i instalujemy Subversion – SVN Team Provider

## Hardware i Software ##

Pierwszym krokiem będzie utworzenie prostego programu z cyklu Witaj Świecie. W tym celu należy utworzyć nowy projekt C++. AVR Cross Target Application. Tylko Release. Wybieramy odpowiedni procesor (w moim przypadku ATmega168) oraz wartość kwarcu zainstalowanego na płytce.

Tutaj warto zauważyć, iż projekt w obecnej formie powstaje pod uC ATmega168 i nie jest on kompatybilny z innym procesorem. Możliwe jest natomiast dopisanie odpowiednich plików pod kolejne wersje ATMEGA. Wszystkie pliki operujące bezpośrednio na rejestrach specyficznych dla ATmega168 są oznaczone postfixem 168.

Prosty program migający diodą LED podłączoną pod port B bit 5.
```
#include <util/delay.h>
#include <avr/io.h>

int main(void) {

	DDRB |= _BV(2);
	for (;;) {
		_delay_ms(100);
		PORTB |= _BV(5);
		_delay_ms(100);
		PORTB &= !_BV(5);
	}

}
```


Czas podłączyć płytkę z ATMEGA do komputera. Niezależnie od tego czy jest to USB czy RS232, w komputerze i tak komunikacja odbywa się przez COM. Po podłączeniu płytki sprawdzamy jaki COM został przydzielony dla naszego AVR. Można to zrobić poprzez menadżer urządzeń w panelu sterowania.  (w linux jest podobnie tyle że nazywa się to dev/stty? Gdzie ? oznacza numer naszego portu. )

Do komunikacji z bootloaderem służy program AVRDude. Wtyczka Eclipse używa tego programu do wgrania pliku HEX do ROMU AVRa.  Wchodzimy w preferencje i ustawienia AVRDude. Dodajemy nową konfigurację. Opcja (-c) wybieramy „arduino”. Opcja (-P) nasz COM w moim przypadku „COM10”. Prędkość transmisji 19200. Następnym krokiem jest ustawienie w preferencjach samego projektu odpowiedniej konfiguracji AVRDude.

Gotowe. Teraz po kliknięciu na toolbarze przycisku z logiem AVR i zieloną strzałką powinno nastąpić załadowanie naszego programu do uC.