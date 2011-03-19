/*
 * TestTask.cpp
 *
 *  Created on: 17-03-2011
 *      Author: radomir.mazon
 */

#include "TestCommand.h"
#include "../util/HexConverter.h"



int TestCommand::test(int a, int b) {
	return a+b;
}

void TestCommand::serialize(char* message, char* result) {
	//X12+43
	//=55 HexConverter::DecToInt(message)
	//return HexConverter::intToDec(55);
	strcpy(result, "odpowiedz");
}

void TestCommand::onTick() {

}
