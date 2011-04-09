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
#include "../model/IntegerModel.h"
#include <string.h>

class TestCommand : public ITick, public ICommand {
public:
	void onTick();

	void serialize(char* message, char* result);
	char getTargetName(){return 'X';}

private:
	int test(int a, int b);
	IntegerModel integerModel;
};

#endif /* TESTTASK_H_ */
