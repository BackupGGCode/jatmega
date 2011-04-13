/*
 * TestTask.h
 *
 *  Created on: 17-03-2011
 *      Author: radomir.mazon
 */

#ifndef TESTTASK_H_
#define TESTTASK_H_

#include "../util/ITick.h"
#include "ICommand.h"
#include "../model/ByteModel.h"
#include <string.h>

class TestCommand : public ICommand {
public:

	void apply(char* message, char* result);
	char getTargetName(){return 'X';}

private:

	ByteModel byteModel;
};

#endif /* TESTTASK_H_ */
