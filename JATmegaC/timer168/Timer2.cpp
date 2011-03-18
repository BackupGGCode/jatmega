/*
 * Timer2.cpp
 *
 *  Created on: 2010-05-06
 *      Author: rmazon
 */

#include "Timer2.h"

Timer2::Timer2() {
	tccrAPointer = &TCCR2A;
	tccrBPointer = &TCCR2B;
	ocrAPointer = &OCR2A;
	ocrBPointer = &OCR2B;
	tcntPointer = &TCNT2;
	*tcntPointer = 0;
	TIMSK2 |= (1<<TOIE2);
	TimerCounter();
}
