/*
 * TestTask.cpp
 *
 *  Created on: 17-03-2011
 *      Author: radomir.mazon
 */

#include "TestCommand.h"
#include <stdlib.h>



int TestCommand::test(int a, int b) {
	return a+b;
}

void TestCommand::serialize(char* message, char* result) {
	integerModel.fromString(message);
	integerModel.setC(test(integerModel.getA(), integerModel.getB()));
	integerModel.toString(result);
}

void TestCommand::onTick() {

}
