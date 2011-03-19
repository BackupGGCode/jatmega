/*
 * ProtocolContriller.cpp
 *
 *  Created on: 12-03-2011
 *      Author: radomir.mazon
 */

#include "ProtocolController.h"
#include "RSController_168.h"

extern RSController rs;

ProtocolController::ProtocolController() {
	tabIndex = 0;
}

//RS informuje obsługę protokołu o odebraniu pełnej linii
//linia zawiera ramke (znak klasy obslugujacej na początku
// i CRC na koncu)
void ProtocolController::onEvent(void* __message) {
	char* _message = (char*)__message;
	char targetName = ((char*)_message)[0];
	char message[120];
	message[0] = 0;
	char result[120];
	result[0] = 0;
	int index = 0;
	while (_message[index]) {
		message[index] = _message[index];
		index++;
	}
	message[index] = _message[index];

	removeProtocolFrame(message);

	int hasTarget = 0;
	for (int i=0; i<tabIndex; i++) {
		if (targetTab[i]->getTargetName() == targetName) {
			targetTab[i]->serialize(message, result);
			hasTarget = 1;
		}
	}

	addProtocolFrame(result, targetName);

	if (hasTarget == 1) {
		rs.sendLine(result);
	}
}

void ProtocolController::removeProtocolFrame(char* message) {
	int index =0;
	while (message[index] != NULL) {
		message[index] = message[index+1];
		index++;
	}
	message[index] = NULL;
}

void ProtocolController::addProtocolFrame(char* message, char targetName) {
	int index =0;
	while (message[index] != NULL){
		index++;
	}
	for (; index>=0; index--) {
		message[index+1] = message[index];
	}

	message[0] = targetName;
}
