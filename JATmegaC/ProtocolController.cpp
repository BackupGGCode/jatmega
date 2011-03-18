/*
 * ProtocolContriller.cpp
 *
 *  Created on: 12-03-2011
 *      Author: radomir.mazon
 */

#include "ProtocolController.h"
#include "RSController_168.h"
#include <string.h>

extern RSController rs;

ProtocolController::ProtocolController() {
	tabIndex = 0;
}

//RS informuje obsługę protokołu o odebraniu pełnej linii
//linia zawiera ramke (znak klasy obslugujacej na początku
// i CRC+znak linii na końcu )
void ProtocolController::onEvent(void* _message) {
	char targetName = ((char*)_message)[0];
	char message[120];
	char* pmessage = message;
	strcpy(pmessage, (char*)_message);
	pmessage = removeProtocolFrame(message);


	char* result = NULL;
	for (int i=0; i<tabIndex; i++) {
		if (targetTab[i]->getTargetName() == targetName) {
			result = targetTab[i]->serialize(pmessage);
		}
	}

	if (result != NULL) {
		rs.sendLine(result);
	}
}

char* ProtocolController::removeProtocolFrame(char* message) {
	message[strlen(message)-1] = NULL;
	message++;
	return message;
}
