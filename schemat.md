#schemat


![http://radomir.mazon.pl/allegro/img/jatmega.jpg](http://radomir.mazon.pl/allegro/img/jatmega.jpg)

Schemat przedstawia sposób udostępnienia wewnętrznych i zewnętrznych zasobów po stronie JAVA. Wykonując operacje na obiektach reprezentujących odpowiednie peryferia układu, następuje wysłanie odpowiedniego polecenia i odzwierciedlenie tej zmiany w uC.


Istnieją obiekty, które reprezentują niektóre operacje samego procesora: IF, FOR. Dzięki temu możliwe jest utworzenie odpowiedniej sekwencji rozkazów zamkniętych w FOR i wysłanie ich do uC. Dzięki temu uC nie może wykonywać pewien szereg operacji bez informowania o nich komputera sterującego. Oczekiwanie na pewne zdarzenie, również może być wysłane do procesowa jaki IF np. czekając na zmianę stanu logicznego pewnego wejścia, nie ma konieczności odpytywania się ciągle przez komputer sterujący o tą wartość. Komputer sterujący może wysłać polecenie oczekiwania na zmianę stanu w poleceniu IF, dzięki czemu zostanie poinformowany dopiero po spełnieniu warunku.