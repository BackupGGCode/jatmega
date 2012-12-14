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
 * jesli SET:
 * 		0 - address
 * 		1 - value  (0x00FF)
 *		(2 -   value (0xFF00) ) tylko dla typow 16bit
 * 	jesli GET:
 * 		0 - frame id
 * 		1 - address
 * 	odpowiedź
 * 		0 - frame ID
 * 		1 - value  (0x00FF)
 *		(3 -  value (0xFF00) )
 */

void mc_apply(saf_Event e) {
	if (e.code == COMMAND_MEMORY_8_SET) {
		uint8_t* address = (uint8_t*)(uint16_t)requestBuffer[e.value].operand[0];
		*address = requestBuffer[e.value].operand[1];
	}
	if (e.code == COMMAND_MEMORY_8_GET) {
		responseBuffer[e.value].count = 2;
		//frame ID
		responseBuffer[e.value].operand[0] = requestBuffer[e.value].operand[0];
		uint8_t* address = (uint8_t*)(uint16_t)requestBuffer[e.value].operand[1];
		responseBuffer[e.value].operand[1] = *address;
		saf_eventBusSend_(RESPONSE_MEMORY_8_GET, e.value);
	}

	if (e.code == COMMAND_MEMORY_16_SET) {
		uint16_t* address = (uint16_t*)(uint16_t)requestBuffer[e.value].operand[0];
		*address = (uint16_t)requestBuffer[e.value].operand[2];
		*address <<= 8;
		*address |= ((uint16_t)requestBuffer[e.value].operand[1]);
	}

	if (e.code == COMMAND_MEMORY_16_GET) {
		responseBuffer[e.value].count = 3;
		//frame ID
		responseBuffer[e.value].operand[0] = requestBuffer[e.value].operand[0];
		uint16_t* address = (uint16_t*)(uint16_t)requestBuffer[e.value].operand[1];
		responseBuffer[e.value].operand[1] = (*address) & 0x00ff;
		responseBuffer[e.value].operand[2] = (*address) >> 8;

		saf_eventBusSend_(RESPONSE_MEMORY_16_GET, e.value);
	}
}

