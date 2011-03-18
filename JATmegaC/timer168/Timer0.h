/*
 * Timer0.h
 *
 *  Created on: 2010-05-06
 *      Author: rmazon
 */

#include "TimerCounter.h"

#ifndef TIMER0_H_
#define TIMER0_H_

class Timer0 : public TimerCounter, public TimerCounter8 {
public:
	Timer0();
};

#endif /* TIMER0_H_ */
