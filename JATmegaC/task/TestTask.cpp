/*
 * TestTask.cpp
 *
 *  Created on: 17-03-2011
 *      Author: radomir.mazon
 */

#include "TestTask.h"
#include "../util/HexConverter.h"
#include <util/delay.h>
#include <avr/io.h>
#include <string.h>

int TestTask::test(int a, int b) {
	return a+b;
}

char* TestTask::serialize(char* message) {
	PORTB |= _BV(5);
	_delay_ms(100);
	PORTB &= !_BV(5);
	_delay_ms(100);
	//X12+43
	//=55 HexConverter::DecToInt(message)
	//return HexConverter::intToDec(55);
	return message;
}

char TestTask::getTargetName() {
	return 'X';
}

void TestTask::onOneEvent() {

}

void TestTask::onLongEvent() {

}

void TestTask::onEndLongEvent() {

}

void TestTask::onMultiEvent(){

}
