#include "Tds18b20.h"

uint8_t Tds18b20::PINPORT = PC4;

void Tds18b20::Initiate()
{
	PORTC |= _BV(PINPORT);
	DDRC |= _BV(PINPORT);		//mozliwosc pisania
	WaitU(500);
}

void Tds18b20::WaitU(uint16_t time)
{
	uint16_t index;
	for (index=0; index<time; index++)
		_delay_us(1);
}

int8_t Tds18b20::Reset_Presence()
{
	int8_t result=0;
	Initiate();
	
	PORTC &= ~_BV(PINPORT);	//zerowy reset 500us
	WaitU(600);
	
	//przejscie na czytanie po podpieciu pull-up

	PORTC |= _BV(PINPORT);
	DDRC &= ~_BV(PINPORT);
	WaitU(15);
	uint16_t counter = 285;
	do
	{
		counter--;
		_delay_us(1);
	}
	while ( ( PINC & _BV(PINPORT) ) && counter>0 );
	if ( counter == 0 )
	{
		result = -1;	//time's out,
	//Error!! Time's out

	}
	else
	{
		//czekamy dopoki na lini jest 0
		counter=0;
		while (!( PINC & _BV(PINPORT) ))
		{
			counter++;
			WaitU(1);
		}
	}
	WaitU(480-counter);
	//Oczywiscie zostawione czytanie z pullup
	return result;
}

void Tds18b20::WriteBit(uint8_t bicik)
{

	DDRC |= _BV(PINPORT);		//ustawienie zero
	PORTC &= ~_BV(PINPORT);
	if ( bicik )
	{
		_delay_us(5);
		PORTC |= _BV(PINPORT);
		DDRC &= ~_BV(PINPORT);
		_delay_us(74);
	}
	else
	{
		_delay_us(15+15+30+15);
		PORTC |= _BV(PINPORT);
		DDRC &= ~_BV(PINPORT);
	}
	
}

void Tds18b20::WriteByte(uint8_t bajt)
{
	
	int8_t index;
	for (index=0;index<8;index++)
	{
		_delay_us(5);
		WriteBit( bajt & (1<<index) );
	}
	_delay_us(3);
}

uint8_t Tds18b20::ReadBit()
{
	uint8_t result=0;
	//wyzerowanie
	DDRC |= _BV(PINPORT);
	PORTC &= ~_BV(PINPORT);
	_delay_us(5);
	//albo odwrotnie aby najpierw zaczac czytac nastepnie wrzucic jedynke
	PORTC |= _BV(PINPORT);	//ustawienie pullup	
	DDRC &= ~_BV(PINPORT);	////ustawienie IN z pullup
	
	uint8_t i=70;
	do
	{
		if (!( PINC & _BV(PINPORT) ))	//jesli wartosc zero to koncz petle
		{
			while (!( PINC & _BV(PINPORT) ));
			result = 0;
			break;
		}
		i--;
		_delay_us(1);		
	}
	while (i>0);
	if ( i == 0 )
		result = 1;
 	_delay_us(i);
	return result;
}

uint8_t Tds18b20::ReadByte()
{
	uint8_t result=0;
	int8_t index;
	for(index=0;index<8;index++)
	{
		_delay_us(5);
		result |= ReadBit()<<index;
	}
	_delay_us(3);	
	return result;
}

void Tds18b20::SetResolution(uint8_t res)
{
	//SetResolution
	PORTC |= _BV(PINPORT);
	DDRC &= ~_BV(PINPORT);
	WaitU(250);

	Reset_Presence();
	//	skip rom
	WriteByte(0xCC);
	//	komenda 0x4E 	Write Scratchpad
	WriteByte(0x4E);
	//alarmy TH i TL ustawione na zero
	WriteByte(0x12);
	WriteByte(0x34);
	//ustawienie resolution
	WriteByte(0x1F | ((res & 0x3)<<5));
	Reset_Presence();
	//skip rom
	WriteByte(0xCC);
	//	komenda 0x48	Copy Scratchpad
	WriteByte(0x48);
	_delay_ms(10);
	while ( !ReadBit() );	//dopoki linia jest low to trwa kopiowanie
	Reset_Presence();	

	//Skonczenie procedury SetResolution
}

uint16_t Tds18b20::CzytajTemperature()
{
	if (Reset_Presence()==-1)
	{
		return 0xffff;
	}
	//	Skip ROM
	WriteByte(0xCC);
	//	Convert T
	WriteByte(0x44);
	while( !ReadBit() );	//dopoki linia jest low to trwa konwersja
	Reset_Presence();
	//	Skip ROM
	WriteByte(0xCC);
	//	Read Scratchpad
	WriteByte(0xBE);
	uint8_t Scratchpad[9];
	uint8_t index;
	for (index=0;index<9;index++) {
		Scratchpad[index] = ReadByte();
	}

	Reset_Presence();
	//	sprawdzenie CRC
	if (CheckCRC(Scratchpad,9) != 0)
	{
		return 0xffff;
	}
	uint16_t result;
	uint8_t *tab = (uint8_t*)&result;
	tab[0] = Scratchpad[0];
	tab[1] = Scratchpad[1];
    //Skonczenie czytania temp
	return result;
}

uint8_t CheckCRC(uint8_t *tab,int tab_size)
{
	uint8_t crc = 0, i;
	for (i = 0; i < tab_size; i++)
		crc = _crc_ibutton_update(crc, tab[i]);
	return crc; // must be 0
}

char* Tds18b20::TempToAscii(uint16_t temperaturka, char* buffor)
{
	if (temperaturka == 0xffff) {
		strcpy(buffor, "ERROR");
		return buffor;
	}
	char a[2];
	double temp = (double)temperaturka;
	temp = temp / 2;
	itoa(temp, buffor, 10);
	strcat(buffor, ",");
	temp -= (int)temp;
	temp *= 10;
	itoa(temp, a, 10);
	strcat(buffor, a);
	return buffor;
}
