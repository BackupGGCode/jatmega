/*
 * main.c
 *
 *  Created on: 02-03-2011
 *      Author: radomir.mazon
 */

#include <util/delay.h>
#include <avr/io.h>
#include "RSController_168.h"
#include "ProtocolController.h"
#include "command/TestCommand.h"

RSController rs;


int main(void) {

	TestCommand testCommand;

	sei();

	DDRB |= _BV(2);

	ProtocolController protocol;
	rs.addListener(&protocol);
	protocol.registerProtocolTarget(&testCommand);


	for (;;) {
		testCommand.onTick();
		rs.onTick();
	}


}

