/*
 * Timer0.cpp
 *
 *  Created on: 2010-05-06
 *      Author: rmazon
 */

#include "Timer0.h"

Timer0::Timer0() {
	tccrAPointer = &TCCR0A;
	tccrBPointer = &TCCR0B;
	ocrAPointer = &OCR0A;
	ocrBPointer = &OCR0B;
	tcntPointer = &TCNT0;
	*tcntPointer = 0;
	TIMSK0 |= (1<<TOIE0);
	TimerCounter();
}
