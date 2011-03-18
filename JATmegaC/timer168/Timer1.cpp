/*
 * Timer1.cpp
 *
 *  Created on: 2010-05-06
 *      Author: rmazon
 */

#include "Timer1.h"

Timer1::Timer1() {
	tccrAPointer = &TCCR1A;
	tccrBPointer = &TCCR1B;
	tccrCPointer = &TCCR1C;
	ocrAPointer = &OCR1A;
	ocrBPointer = &OCR1B;
	tcntPointer = &TCNT1;
	*tcntPointer = 0;
	TIMSK1 |= (1<<TOIE1);
	TimerCounter();
};

