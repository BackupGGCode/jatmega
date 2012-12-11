/*
 * TestTask.c
 *
 *  Created on: 17-03-2011
 *      Author: radomir.mazon
 *
 *      Test polega na wykonaniu operacji dodania
 *      request = operandA + operandB + operandC
 */

#include "TestCommand.h"

extern Message_t requestBuffer[MESSAGE_BUFFER_SIZE];
extern Message_t responseBuffer[MESSAGE_BUFFER_SIZE];

/**
 * Komenda testowa jest synchroniczna, oznacza to ze nadawca czeka na odpowiedz.
 * Aby mozliwe bylo parowanie odpowiedzi z odpowiednim pytaniem w operandzie 0
 * zawsze zapisany jest zpecjalny numer ramki, ktory trzeba zwrocic.
 */

void tc_apply(saf_Event event) {
	if (event.code == COMMAND_TEST) {
		//zaznaczamy ze ta odpowiedz jest na konkretne pytanie
		responseBuffer[event.value].operand[0] = requestBuffer[event.value].operand[0];

		//logika:
		responseBuffer[event.value].operand[1] = 0;
		for (uint8_t i=0; i<requestBuffer[event.value].count; i++) {
			responseBuffer[event.value].operand[1] += requestBuffer[event.value].operand[i];
		}
		responseBuffer[event.value].count = 2;
		saf_eventBusSend_(RESPONSE_TEST, event.value);
	}
}
