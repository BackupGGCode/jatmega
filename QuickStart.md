# Jak zacząć... (dla wersji 0.1) #

1. Wgrywamy plik "hex" do ATMEGA
2. Podpisany jatmega-xxx.jar i potrzebne liby do classpath.
3. Tworzymy obiekt reprezentujący avr.

```
ATMEGA168 atmega = (ATMEGA168) ATMEGAFactory.getATMEGA(ATMEGAFactory.ATMEGA168);
```

4. W tej wersji dostępne tylko operacje na pamięci. Przykład
```
atmega.set(atmega.io.DDRB, 0xff);
atmega.set(atmega.io.PORTB, 0xAA);
```

GOTOWE :)