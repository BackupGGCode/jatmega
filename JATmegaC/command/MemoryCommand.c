/*
 * MemoryCommand_16.c
 *
 *  Created on: 28-11-2011
 *      Author: radomir.mazon
 *
 *	Operacja zapisu komórki pamieci o 16 bitowym adresie
 *	*(operandA_operandB) = operandC
 *
 *	Operacja nie zwraca odpowiedzi

 */

#include "MemoryCommand.h"

extern Message_t requestBuffer[MESSAGE_BUFFER_SIZE];
extern Message_t responseBuffer[MESSAGE_BUFFER_SIZE];

void mc_apply(saf_Event event) {
	if (event.code == COMMAND_MEMORY) {
		//TODO
	}
}

/**
void MemoryCommand::apply(char* request, char* response) {
	ByteModel model;
	ByteModel resp;
	model.fromString(request);
	if (model.getSize() == 3) {
		uint8_t* address = (uint8_t*)model.get(0);
		uint8_t  value = model.get(1);
		uint8_t  operation = model.get(2);
		if (operation == 'S') {
			*address = value;
		} else if (operation == 'A') {
			*address = *address & value;
		} else if (operation == 'O') {
			*address = *address | value;
		}
		resp.add(*address);
	} else if (model.getSize() == 6) {
		uint16_t* address = (uint16_t*)model.get(1);

		uint8_t  value1 = model.get(2);
		uint8_t  value2 = model.get(3);
		uint16_t value = (value1<<8) | value2;
		uint8_t  operation = model.get(5);
		if (operation == 'S') {
			*address = value;
		} else if (operation == 'A') {
			*address = *address & value;
		} else if (operation == 'O') {
			*address = *address | value;
		}
		value = *address;
		resp.add(value>>8);
		resp.add((uint8_t)value);
	}

	resp.toString(response);
}
**/
