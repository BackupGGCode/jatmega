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

/**
 * tablice globalne
 */
Message_t responseBuffer[MESSAGE_BUFFER_SIZE];
Message_t requestBuffer[MESSAGE_BUFFER_SIZE];


uint8_t _pc_frameBuffer[COMMAND_BUFFER_SIZE];
uint8_t _pc_frameIndex =0; //zawsze pokazuje na pierwszy wolny...
uint8_t _pc_requestIndex =0;
uint8_t _pc_isReceivedPEOM = 0;

void pc_onEnevt(uint8_t code, int value) {

	if (code == EVENT_RS_RECEIVE) {
		uint8_t messageChar = (uint8_t)value;
		if (messageChar == PEOM) {
			if (_pc_isReceivedPEOM == 0) {
				_pc_isReceivedPEOM = 1;
			} else {
				_pc_isReceivedPEOM = 0;
			}
		}

		if (messageChar == EOM && _pc_isReceivedPEOM == 0) {
			_pc_applyCommand();
			_pc_frameIndex = 0;
		} else {
			if (_pc_frameIndex<COMMAND_BUFFER_SIZE) {
				_pc_frameBuffer[_pc_frameIndex] = messageChar;
			}
		}

		_pc_frameIndex++;
		if (_pc_frameIndex>COMMAND_BUFFER_SIZE) {
			//koniec zasiegu... cos poszlo nie tak, odebrano wicej znakow niz przewidziano w ramce
			_pc_frameIndex = COMMAND_BUFFER_SIZE;
			saf_eventBusSend(EVENT_ERROR, ERROR_RECIVE_TOO_MANY);
		}
	}

	/**
	 * przyechwytywanie wszystkich RESPONSE_
	 */
	if ((code & 0xF0) == RESPONSE_GROUP) {
		_pc_applyResponse(code, value);
	}
}

void _pc_applyCommand() {
	//walidacja
	if (_pc_frameIndex == 0 || _pc_frameIndex) {
		saf_eventBusSend(EVENT_ERROR, ERROR_INDEX_FRAME);
		return;
	}

	uint8_t eventCommandCode = ((_pc_frameBuffer[0] & 0xF0)>>4 ) | 0xF0;
	//uint8_t controllCode	 = commandBuffer[0] & 0x0F;
	uint8_t messageIndex = pc_getMessageIndex();

	requestBuffer[messageIndex].count = _pc_frameIndex;
	for (uint8_t i=0; i<_pc_frameIndex; i++) {
		requestBuffer[messageIndex].operand[i] = _pc_frameBuffer[i+1];
	}
	saf_eventBusSend(eventCommandCode, _pc_requestIndex);
}

void _pc_applyResponse(uint8_t code, int value) {
	//walidacja
	if (value >=MESSAGE_BUFFER_SIZE || responseBuffer[value].count >(COMMAND_BUFFER_SIZE-1)) {
		saf_eventBusSend(EVENT_ERROR, ERROR_RESPONSE);
		return;
	}

	uint8_t commandCode = code << 4;
	//tutaj powinna byc policzona suma kontrolna i dodana do commandCode na 4 mlodszych bitach
	saf_eventBusSend(EVENT_RS_SEND, commandCode);
	for (uint8_t i=0; i<responseBuffer[value].count; i++) {
		uint8_t tosend = responseBuffer[value].operand[i];
		if (tosend == EOM) {
			saf_eventBusSend(EVENT_RS_SEND, PEOM);
		}
		saf_eventBusSend(EVENT_RS_SEND, tosend);
	}
}

uint8_t pc_getMessageIndex() {
	uint8_t result = _pc_requestIndex;
	_pc_requestIndex++;
	if (_pc_requestIndex>=MESSAGE_BUFFER_SIZE) {
		_pc_requestIndex=0;
	}
	return result;
}
