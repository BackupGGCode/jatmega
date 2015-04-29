#Wprowadzenie do projektu

# Do kogo jest kierowany projekt #

  * Do osób które chcą ograniczyć wykonanie kodu po stronie AVR i zmniejszyć ilość programowania uC.
  * Do osób nie chcących ograniczać swojej wyobraźni do kilku kilobajtów pamięci programu.
  * Do osób, które nie lubią programować uC w języku  C, czy C++, lub sprawia im to trudności.

# Cel #

Programowanie w JAVA pod uC AVR (ATMEGA168) .
Wyeliminowanie programowania uC  AVR.
Przeniesienie logiki systemu do zewnętrznego komputera (Laptop, Smartphome).
Wykorzystanie możliwości jakie dają zewnętrzne zasoby (akcelerometr,  G-sensor, GPS,  rozpoznanie mowy, Interfejs dotykowy, graficzne prezentowanie wyników) bez konieczności programowania ich na niskim poziomie.

# Spojrzenie z góry #

Kontroler ATMEGA jest tani i ma niewielkie rozmiary. Świetnie nadaje się do sterowania serwomechanizmami, czujnikami, oświetleniem itp. Można wykorzystać bezpośrednio jego porty jako końcówki operacyjne wejścia wyjścia cyfrowe czy analogowe, wbudowanego PWM lub sterować innymi peryferiami za pomocą wbudowanych magistrali. W Internecie jest dużo ciekawych aplikacji tego popularnego uC.

Zbudowanie odpowiedniej aplikacji dla nikogo nie stanowi problemu. Przeważnie sprowadza się to do przylutowania kilku „kabelków” bezpośrednio do urządzenia sterowanego przez uC. Większość najpopularniejszych układów peryferyjnych takich jak LED, ekrany LCD, serwa, czujniki ultradźwiękowe, przekaźniki itp. została dobrze opisana z łatwością można znaleźć schemat ich podłączenia.

Aplikacje, których sercem jest uC, mają jedną poważną wadę – po zbudowaniu wszystkiego, trzeba to teraz oprogramować. I tutaj pojawia się problem. Istnieją dwa typy ludzi, „rasowy elektronik minimalista” i „wygodny leń”. Rasowy elektronik to człowiek, który uważa, że wszystko trzeba oprogramować w C a najlepiej to ASM i to jeszcze optymalizując do maksimum kod, głowiąc się jak to napisać aby kod wynikowy programu miał jak najmniejszy rozmiar. Druga grupa ludzi, do których ja też się zaliczam, to ludzie programujący w językach bardziej przyjaznych człowiekowi takich jak JAVA czy C#. Ponieważ jestem zwolennikiem JAVA, C# sobie darujemy. Zatem mamy klasyczny dylemat: akwarium, albo rybki… Oczywiści można programować pewnie w JAVA kontrolery, ale są one drogie i nieosiągalne dla zwykłego elektronika hobbysty. Myślę tutaj o ARM.

Wiemy już jaki jest problem. Chcemy budować urządzenia, którego sercem jest tani uC i które nie są skomplikowane (czytaj, nie ma w nich elektroniki analogowej), jednocześnie chcielibyśmy aby robiły wspaniałe rzeczy (tutaj czytaj, rozpoznawały mowę, były sterowane położeniem w przestrzeni, czy zdalnie sterowane przez Internet) i na dodatek pisać oprogramowanie w JAVA z wykorzystaniem wszystkich dobrodziejstw tego języka…

Czy to w ogóle możliwe?

Okazuje się że TAK. Oczywiście nie brakuje pewnych kompromisów.

# Jaka jest idea #

Jakiś czas temu, gdy szukałem ciekawych rozwiązań w programowaniu AVRów natknąłem się na kilka implementacji mini systemu operacyjnego dla tego kontrolera. Okazało się szybko że zajmują dużo RAMu i mają bardzo ograniczone możliwości. Później poszukałem czy nie można by użyć błogosławieństwa obiektowości w programowaniu. Okazało się że tak, ale w pewnym stopniu. Dołączenie procedur mallock() i free(), zjadło mi większość pamięci programu. Ostatecznie bez tych dwóch funkcji też można programować obiektowo, ale C++ to nie JAVA. Mimo iż swoje początki kariery zawodowej zawdzięczam C++ (rok czasu), to jednak JAVA sprawia, że programowanie jest przyjemne. Zacząłem więc szukać czy jakiś szalony naukowiec nie próbował uruchomić na ATMEGA maszyny wirtualnej, a przynajmniej jej mikro edycji… Niestety…. Dopiero ARMy mają takie możliwości.

Pomysł przyszedł mi do głowy w chwili gdy zacząłem programować w GWT (Google Web Toolkit). To bardzo sprytne rozwiązanie, które wykorzystuje kod napisany w JAVA do skośnej kompilacji na inny język, w tym przypadku JavaScript. A gdyby tak dało się zrobić to samo z AVR ?

# Rozwiązanie #

Mamy zwyczaj traktować uC jak czarną skrzynkę. Piszemy program, wgrywany do tej czarnej skrzynki i w chwili odłączenia programatora, procesor zaczyna żyć własnym życiem. Niczym Doktor Frankenstein, tchniemy nowe życie przy każdym restarcie do naszego AVR, podziwiając jak uczy się chodzić. Jednocześnie niestety hermetyzujemy naszego potwora nie pozwalając mu się uczyć. Często bywa tak, że siadając po miesiącu do programu, chcąc coś przerobić, musimy się go uczyć od nowa. Zwłaszcza jak to jest ASM. A gdyby tak potraktować uC jako ślepego wykonawcę jakiegoś większego super komputera ?

Pomysł polega na tym, aby kod uC nie wykonywał programu ale potrafił wykonywać pewne rozkazy. Rozwiązanie to jest stare jak świat. Przykładem jest język BASIC w Atari, PHP czy BASH. Czyli języki skryptowe. Gdyby tak kod wgrany do ATMEGA potrafił wykonywać dowolną permutację określonej grupy rozkazów (w tym IF i FOR) to nie musielibyśmy już więcej go programować :D EUREKA.

Naturalnym kontaktem w czasie rzeczywistym z ATMEGA jest RS232 do niego wbudowany. Można w ten sposób wysyłać pewne rozkazy lub skrypt do wykonania. RS232 łatwo można zastąpić na przykład modułem Bluetooth. Po drugiej stronie stawiamy interfejs JAVA, który z jednej strony łączy się przez Bluetooth z kontrolerem wysyłając mu odpowiednie rozkazy, a z drugiej strony udostępnia interfejs programiście.

I tak mamy możliwość programowania w JAVA pod ATMEGA….

# Ograniczenia #

Nie obyło się bez kompromisów. Przede wszystkim zawsze w pobliżu naszego urządzenia z uC (100m) musi znajdować się komputer sterujący. Czas potrzebny na przesłanie rozkazów znacznie spowalnia pracę uC co znacząco ogranicza zastosowanie tego typu rozwiązania.