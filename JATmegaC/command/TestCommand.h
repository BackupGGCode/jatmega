/*
 * TestTask.h
 *
 *  Created on: 17-03-2011
 *      Author: radomir.mazon
 */

#ifndef TESTTASK_H_
#define TESTTASK_H_

#include "../util/ITick.h"
#include "../ProtocolTarget.h"
#include "../model/IntegerModel.h"
#include <string.h>

class TestCommand : public ITick, public ProtocolTarget {
public:
	void onTick();

	void serialize(char* message, char* result);
	char getTargetName(){return 'X';}

private:
	int test(int a, int b);
	IntegerModel integerModel;
};

#endif /* TESTTASK_H_ */
