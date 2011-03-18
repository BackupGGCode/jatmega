/*
 * Timer2.h
 *
 *  Created on: 2010-05-06
 *      Author: rmazon
 */
#include "TimerCounter.h"

#ifndef TIMER2_H_
#define TIMER2_H_

class Timer2 : public TimerCounter, public TimerCounter8 {
public:
	Timer2();
};

#endif /* TIMER2_H_ */
