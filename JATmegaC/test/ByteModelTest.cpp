/*
 * ByteModelTest.cpp
 *
 *  Created on: 10-04-2011
 *      Author: radomir.mazon
 */

#include "../RSController_168.h"
#include "../model/ByteModel.h"

extern RSController rs;


void byteModelTest(void) {
#ifdef TEST_ENABLED
	//main loop
	sei();

	ByteModel model;

	model.add(0x13);
	model.add(0x07);
	model.add(model.get(0) + model.get(1));

	char temp[10] = {0};
	rs.send(model.toString(temp));

	for(;;){}
#endif
}
