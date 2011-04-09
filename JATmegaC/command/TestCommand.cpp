/*
 * TestTask.cpp
 *
 *  Created on: 17-03-2011
 *      Author: radomir.mazon
 */

#include "TestCommand.h"
#include <stdlib.h>


void TestCommand::serialize(char* request, char* response) {
	integerModel.fromString(request);
	integerModel.setC(
			integerModel.getA() + integerModel.getB()
			);
	integerModel.toString(response);
}

void TestCommand::onTick() {

}
