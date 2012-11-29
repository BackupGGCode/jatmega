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

void tc_apply(saf_Event event) {
	if (event.code == COMMAND_TEST) {
		responseBuffer[event.value].operand[0] = requestBuffer[event.value].operand[0] +
			requestBuffer[event.value].operand[1] + requestBuffer[event.value].operand[2];
		responseBuffer[event.value].count = 1;
		saf_eventBusSend_(RESPONSE_TEST, event.value);
	}
}
