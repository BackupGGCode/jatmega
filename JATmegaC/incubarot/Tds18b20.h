/*
	Franciszek Mikłaszewicz
	31.03.2006
	(zmieniona metoda Tds18b20::TempToAscii przez radomir.mazon)
*/

#include <inttypes.h>
#include <avr/io.h>
#include <util/delay.h>
#include <util/crc16.h>
#include <stdlib.h> 
#include <string.h>

class Tds18b20
{
	private:
		int8_t Reset_Presence();
		void Initiate();
		void WaitU(uint16_t time);
		void WriteBit(uint8_t bicik);
	public:
		static uint8_t PINPORT;//musi to byc port typu C: domy�lnie PC4
		void WriteByte(uint8_t bajt);
		uint8_t ReadBit();
		uint8_t ReadByte();
		/*
			Default 3;
			0	-	9bit
			1	-	10bit
			2	-	11bit
			3	-	12bit
		*/
		void SetResolution(uint8_t res);
		uint16_t CzytajTemperature();
		static char* TempToAscii(uint16_t temp, char* buffor);	//zwrocenie zera oznacza blad
		Tds18b20() {Initiate();};
};


//sprawdzanie CRC. Jesli zwroci zero, to dane najprawdopodobniej poprawne
uint8_t CheckCRC(uint8_t *tab,int tab_size);
