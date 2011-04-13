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
#include <stdlib.h>

#include "model/ByteModel.h"
#include "incubarot/Tds18b20.h"

RSController rs;


int main(void) {

	//komendy
	TestCommand testCommand;

	//konfiguracja protokolu
	ProtocolController protocol;
	rs.addListener(&protocol);

	//powiazanie komend z protokoloem
	protocol.registerProtocolTarget(&testCommand);

	//main loop
	sei();

	for (;;) {
		rs.onTick();
	}
}



