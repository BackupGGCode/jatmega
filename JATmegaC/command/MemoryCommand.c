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

/**
 * Operacja SET czy GET jest zakodowana w ilosci otrzymanych operandow
 * jesli SET:
 * 		0 - frame id
 * 		1 - address
 * 		2 - value
 * 	jesli GET:
 * 		0 - frame id
 * 		1 - address
 * 	odpowiedź
 * 		0 - frame ID
 * 		1 - value
 */

void mc_apply(saf_Event e) {
	if (e.code == COMMAND_MEMORY) {
		responseBuffer[e.value].count = 2;
		//frame ID
		responseBuffer[e.value].operand[0] = requestBuffer[e.value].operand[0];
		uint8_t* address = (uint8_t*)(uint16_t)requestBuffer[e.value].operand[1];
		if (requestBuffer[e.value].count == 3) {
			//SET
			*address = requestBuffer[e.value].operand[2];
		}
		//GET
		responseBuffer[e.value].operand[1] = *address;

		saf_eventBusSend_(RESPONSE_MEMORY, e.value);
	}
}

