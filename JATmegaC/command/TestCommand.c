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

void tc_apply(uint8_t code, int value) {
	if (code == COMMAND_TEST) {
		responseBuffer[value].operand[0] = requestBuffer[value].operand[0] +
			requestBuffer[value].operand[1] + requestBuffer[value].operand[2];
		responseBuffer[value].count = 1;
		saf_eventBusSend(RESPONSE_TEST, value);
	}
}
