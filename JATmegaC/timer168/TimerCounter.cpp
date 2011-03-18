/*
 * TimerCounter.cpp
 *
 *  Created on: 2010-05-03
 *      Author: rmazon
 */

#include "TimerCounter.h"
#define CS_CLEAR 0xf8
#define WGMB_CLEAR 0xe7
#define WGMA_CLEAR 0xfc
#define COMA_CLEAR 0x3f
#define COMB_CLEAR 0xcf

TimerCounter::TimerCounter() {
	*tccrBPointer = 0;
	*tccrAPointer = 0;
	setPrescaler(CLK);
	stop();
}

void TimerCounter::start() {
	*tccrBPointer &= CS_CLEAR;
	*tccrBPointer |= ~(CS_CLEAR | ~prescalerState);
}

void TimerCounter::stop() {
	*tccrBPointer &= CS_CLEAR;
}

void TimerCounter::setPrescaler(char pre) {
	prescalerState = pre;
}

void TimerCounter::setNormalMode() {
	*tccrAPointer &= WGMA_CLEAR;
	*tccrBPointer &= WGMB_CLEAR;
}

void TimerCounter::setCtcMode() {
	*tccrAPointer &= WGMA_CLEAR;
	*tccrBPointer |= ~WGMB_CLEAR;
}

void TimerCounter::setPwmMode() {
	*tccrAPointer |= ~WGMA_CLEAR;
	*tccrBPointer &= WGMB_CLEAR;
}

void TimerCounter::setPwmMode(uint8_t mode) {
	*tccrAPointer &= WGMA_CLEAR;
	*tccrAPointer |= ~(WGMA_CLEAR | ~mode);
	*tccrBPointer &= WGMB_CLEAR;
}

void TimerCounter::outputNotConnectedA() {
	*tccrAPointer &= COMA_CLEAR;
}

void TimerCounter::outputNotConnectedB() {
	*tccrBPointer &= COMB_CLEAR;
}

void TimerCounter::outputNotInvertA() {
	*tccrAPointer &= COMA_CLEAR;
	*tccrAPointer |= 0b10000000;
}

void TimerCounter::outputNotInvertB() {
	*tccrAPointer &= COMB_CLEAR;
	*tccrAPointer |= 00100000;
}

void TimerCounter::outputInvertA() {
	*tccrAPointer &= COMA_CLEAR;
	*tccrAPointer |= 0b11000000;
}

void TimerCounter::outputInvertB() {
	*tccrAPointer &= COMB_CLEAR;
	*tccrAPointer |= 0b00110000;
}
