# Instalacja #

Sterownik RS232 wymaga instalacji odpowiedniej biblioteki po stronie JAVA.

### Windows ###
Plik JATmegaJava\rxtx-2.1-7-bins-[r2](https://code.google.com/p/jatmega/source/detail?r=2)\Windows\i368-mingw32\rxtxSerial.dll przegrać do JAVA\_HOME\jre\bin

### Linux ###

todo

### Solaris ###

todo

### Mac ###

todo

### Android ###

todo


# IDEA #

Model komunikacyjny jest modelem warstwowym.

  * Warstwa aplikacji
  * Warstwa protokołu (+kontrola CRC)
  * Warstwa komunikacji
  * Warstwa fizyczna


# JAVA #

Warstwa komunikacji reprezentowana jest przez IBus. Na tym poziomie komunikacja jest tekstowa. Brak programowej kontroli poprawności przesyłanych danych. Kontrola jedynie na poziomie warstwy fizycznej. Implementacją IBus jest odpowiedni sterownik, który można zdefiniować w pliku config.properties.

```
interface=RS232
baudRate=19200
windows.driver=gnu.io.RXTXCommDriver
windows.port=COM10
linux.driver=gnu.io.RXTXCommDriver
linux.port=/dev/ttyS0
```

Właściwość "interface" określa rodzaj sterownika, który zostanie załadowany. Reszta konfiguracji jest specyficzna dla danego sterownika.

Sterownik tworzony jest automatycznie. Klasą którą należy poprosić o utworzenie sterownika jest BusManager.

```
      IBus bus = BusManager.getInstance().getBus();
```

Zostanie załadowana odpowiednia konfiguracja (można to zrobić ręcznie tworząc odpowiednią klasę implementującą interfejs IBusConfig) i utworzony odpowiedni sterownik.

Aby aplikacja mogła otrzymywać zdarzenia z warstwy komunikacji można dodać callback zdarzeń.

```

		bus.addEventCallback(new IBusEventCallback() {
			
			@Override
			public void driverLoadFailuerEvent() {
				//...
			}
			
			@Override
			public void disconnectEvent() {
				//...
			}
			
			@Override
			public void connectEvent() {
				//...
			}
		});

```