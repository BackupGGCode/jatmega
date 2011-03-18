/*
 * Timer1.h
 *
 *  Created on: 2010-05-06
 *      Author: rmazon
 */
#include "TimerCounter.h"

#ifndef TIMER1_H_
#define TIMER1_H_

class Timer1 : public TimerCounter, public TimerCounter16 {
public:
	Timer1();
};

#endif /* TIMER1_H_ */
