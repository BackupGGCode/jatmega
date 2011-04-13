/*
 * TimerCounter.h
 *
 *  Created on: 2010-05-03
 *      Author: rmazon
 */
#include <avr/io.h>

#ifndef TIMERCOUNTER_H_
#define TIMERCOUNTER_H_

class TimerCounter {
public:
	//prescaler
	static const char OFFLINE = 0;
	static const char CLK = 1;
	static const char CLK8 = 2;
	static const char CLK64 = 3;
	static const char CLK256 = 4;
	static const char CLK1024 = 5;
	static const char EXT_SOURCE_FALLING_EDGE = 6;
	static const char EXT_SOURCE_RISING_EDGE = 7;

	static const char PWM_8_BIT =1;
	static const char PWM_9_BIT =2;
	static const char PWM_10_BIT =3;

	TimerCounter();

	void start();
	void stop();

	/**
	 * Wyj�cie nie pod��czone
	 */
	void outputNotConnectedA();
	void outputNotConnectedB();
	/**
	 * Wyj�cie kasowane podczas por�wnania przy liczeniu w g�r�
	 * Wyj�cie ustawiane podczas por�wnania przy liczeniu w du�
	 * PWM bez inversji
	 */
	void outputNotInvertA();
	void outputNotInvertB();
	/**
	 * Wyj�cie kasowane podczas por�wnania przy liczeniu w du�
	 * Wyj�cie ustawiane podczas por�wnania przy liczeniu w g�r�
	 * PWM z inwersj�
	 */
	void outputInvertA();
	void outputInvertB();
	/**
	 * preskaler
	 */
	void setPrescaler(char);
	/**
	 * Tryb pracy timera PWM
	 */
	void setPwmMode();
	void setPwmMode(uint8_t mode);

	/**
	 * Tryb  z prze��dowaniem
	 */
	void setCtcMode();
	/**
	 * Normalny tryb pracy licznika
	 */
	void setNormalMode();


protected:
	uint8_t prescalerState;
	volatile uint8_t* tccrAPointer;
	volatile uint8_t* tccrBPointer;
	volatile uint8_t* tccrCPointer;
};

class TimerCounter8 {
protected:
	volatile uint8_t* ocrAPointer;
	volatile uint8_t* ocrBPointer;
	volatile uint8_t* tcntPointer;

public:
	char getCounter() {
		return *tcntPointer;
	}
	void setCounter(char counter) {
		*tcntPointer = counter;
	}
	char getOutputCompareA() {
		return *ocrAPointer;
	}
	volatile uint8_t* getOutputCompareAPointer() {
			return ocrAPointer;
	}
	void setOutputCompareA(char ocA) {
		*ocrAPointer = ocA;
	}
	char getOutputCompareB() {
			return *ocrBPointer;
	}
	volatile uint8_t* getOutputCompareBPointer() {
				return ocrBPointer;
	}
	void setOutputCompareB(char ocB) {
			*ocrBPointer = ocB;
	}
};

class TimerCounter16 {
protected:
	volatile uint16_t* ocrAPointer;
	volatile uint16_t* ocrBPointer;
	volatile uint16_t* icrPointer;
	volatile uint16_t* tcntPointer;
public:
	uint8_t getCounter() {
		return *tcntPointer;
	}
	void setCounter(uint8_t counter) {
		*tcntPointer = counter;
	}
	uint8_t getOutputCompareA() {
		return *ocrAPointer;
	}
	void setOutputCompareA(uint8_t ocA) {
		*ocrAPointer = ocA;
	}
	uint8_t getOutputCompareB() {
			return *ocrBPointer;
	}
	void setOutputCompareB(uint8_t ocB) {
			*ocrBPointer = ocB;
	}
	uint8_t getInputCapture() {
			return *icrPointer;
	}
	void setInputCapture(uint8_t ic) {
			*icrPointer = ic;
	}
};
#endif /* TIMERCOUNTER_H_ */
