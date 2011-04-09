/*
 * ProtocolContriller.cpp
 *
 *	Komenda sklada sie z:
 *	Xa_____CRC\n
 *	X - znak komendy
 *	a - znacznik komunikatu, zwiększany przy każdym komunikacie
 *	___ - wiadomosc o dowolnej dlugosci (nie wiecej niz 100 znakow
 *	CRC - kod CRC
 *	\n koniec linii = koniec komunikatu
 *
 *	Komunikat zwrotny taki sam. Rozni sie jedunie trescia ___
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

	int len = 0;
	while (*(_message+len)) {len++;}
	if (len<3) {
		return;
	}

	char targetName = _message[0];
	char targetNum = _message[1];
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

	for (int i=0; i<tabIndex; i++) {
		if (targetTab[i]->getTargetName() == targetName) {
			targetTab[i]->serialize(message, result);
			addProtocolFrame(result, targetName, targetNum);
			rs.sendLine(result);
			break;
		}
	}
}

void ProtocolController::removeProtocolFrame(char* message) {
	int index =0;
	while (message[index] != 0) {
		message[index] = message[index+2];
		index++;
	}
	message[index] = 0;
}

void ProtocolController::addProtocolFrame(char* message, char targetName, char targetNum) {
	int index =0;
	while (message[index] != 0){
		index++;
	}
	for (; index>=0; index--) {
		message[index+2] = message[index];
	}

	message[0] = targetName;
	message[1] = targetNum;
}
