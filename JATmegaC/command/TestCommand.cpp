/*
 * TestTask.cpp
 *
 *  Created on: 17-03-2011
 *      Author: radomir.mazon
 */

#include "TestCommand.h"
#include <stdlib.h>


void TestCommand::apply(char* request, char* response) {
	byteModel.fromString(request);
	byteModel.add(
			byteModel.get(0) + byteModel.get(1)
			);
	byteModel.toString(response);
}
