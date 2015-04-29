Komunikacja odbywa się na zasadzie PYTANIE - ODPOWIEDZ.

# Budowa ramki komunikacyjnej #

**Xa**~wiadomosc~**CRC**\n

  * X - Unikalny symbol określany metodą **getTargetName()** w klasie Command
  * a - znak licznika wysyłanego komunikatu. Jest to kolejny znak z zakresu abc.. - ...XYZ. Dzięki tej wartości w sytuacji wysłania przez komputer dwóch komunikatów w krótkim odstępie czasu po odebraniu odpowiedzi wiadomo do którego jest odpowiedź.
  * wiadomosc - to dowolny ciąg znaków dostarczany bezpośrednio do obsługi komunikatu metodzie synchronize().
  * CRC - opcjonalna kontrola CRC (chwilowo nie ma, przy połączeniu bluetooth nie trzeba :))
  * \n - znacznik końca linii. Jedna wiadomość to jedna linia.

# Scenariusz #

Inicjatorem może być komputer lub AVR. Komenda po stronie AVR może być pasywna lub aktywna.

C.d.n.