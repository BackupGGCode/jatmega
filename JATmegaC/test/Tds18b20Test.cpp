/*
 * Tds18b20Test.cpp
 *
 *  Created on: 10-04-2011
 *      Author: radomir.mazon
 */

#include "Tds18b20Test.h"
#include <util/delay.h>
#include <avr/io.h>
#include "../RSController_168.h"
#include <stdlib.h>

#include "../incubarot/Tds18b20.h"

extern RSController rs;


void Tds18b20Test(void) {
#ifdef TEST_ENABLED
	//main loop
	sei();

	Tds18b20 termometr;
	termometr.SetResolution(3);
	int16_t temperatura;

	for (;;) {
		temperatura = termometr.CzytajTemperature();
		char buffor[10];
		rs.sendLine(Tds18b20::TempToAscii(temperatura, buffor));
	}
#endif
}

