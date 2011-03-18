/*
 * TestTask.h
 *
 *  Created on: 17-03-2011
 *      Author: radomir.mazon
 */

#ifndef TESTTASK_H_
#define TESTTASK_H_

#include "../util/EventController.h"
#include "../ProtocolTarget.h"

class TestTask : public EventController, public ProtocolTarget {
public:
	void onOneEvent();
	void onLongEvent();
	void onEndLongEvent();
	void onMultiEvent();

	char* serialize(char* message);
	char getTargetName();
private:
	int test(int a, int b);

};

#endif /* TESTTASK_H_ */
