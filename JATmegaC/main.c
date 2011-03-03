/*
 * main.c
 *
 *  Created on: 02-03-2011
 *      Author: radomir.mazon
 */

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

