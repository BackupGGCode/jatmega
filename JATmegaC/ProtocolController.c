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


uint8_t _pc_frameBuffer[FRAME_BUFFER_SIZE];
uint8_t _pc_frameIndex =0; //zawsze pokazuje na pierwszy wolny...
uint8_t _pc_requestIndex =0;
uint8_t _pc_isReceivedPEOM = 0;

void pc_onEnevt(saf_Event event) {

	if (event.code == EVENT_RS_RECEIVE) {
		uint8_t isEOM = event.value == EOM && _pc_isReceivedPEOM == 0;
		if (event.value == PEOM && _pc_isReceivedPEOM == 0) {
			_pc_isReceivedPEOM = 1;
			return;
		}
		_pc_isReceivedPEOM = 0;

		if (isEOM) {
			_pc_applyCommand();
			_pc_frameIndex = 0;
		} else {
			if (_pc_frameIndex < FRAME_BUFFER_SIZE) {
				_pc_frameBuffer[_pc_frameIndex] = event.value;
			}
			_pc_frameIndex++;
			if (_pc_frameIndex>FRAME_BUFFER_SIZE) {
				//Cala ramka zostala odebrama. Odebrano jedka dodatkowe dane
				_pc_frameIndex = FRAME_BUFFER_SIZE;
				saf_eventBusSend_(EVENT_FRAME_OVERFLOW, event.value);
			}
		}
	}

	/**
	 * przyechwytywanie wszystkich RESPONSE_
	 */
	if ((event.code & 0xF0) == RESPONSE_GROUP) {
		_pc_applyResponse(event.code, event.value);
	}
}

/**
 * UWAGA. sume liczymy tylko z operandów.
 * zatem do symy nie liczy sie znaku PEOM
 */
uint8_t _pc_isControllCodeOK() {
	uint8_t sum = 0;
	for (uint8_t i=1; i<_pc_frameIndex; i++) {
		sum += _pc_frameBuffer[i];
	}
	return (_pc_frameBuffer[0] & 0x0F) == (sum&0x0f);
}

void _pc_applyCommand() {
	//walidacja
	if (_pc_frameIndex == 0) {
		saf_eventBusSend_(EVENT_ERROR, ERROR_INDEX_FRAME);
		return;
	}

	uint8_t eventCommandCode = ((_pc_frameBuffer[0] & 0xF0)>>4 ) | 0xF0;
	if (!_pc_isControllCodeOK()) {
		saf_eventBusSend_(EVENT_ERROR, ERROR_CONTROLL_CODE);
		return;
	}
	uint8_t messageIndex = pc_getMessageIndex();

	requestBuffer[messageIndex].count = _pc_frameIndex-1;
	for (uint8_t i=0; i<_pc_frameIndex-1; i++) {
		requestBuffer[messageIndex].operand[i] = _pc_frameBuffer[i+1];
	}
	saf_eventBusSend_(eventCommandCode, messageIndex);
}

uint8_t _pc_getControllCode(uint8_t value) {
	uint8_t sum =0;
	for (uint8_t i=0; i<responseBuffer[value].count; i++) {
		sum += responseBuffer[value].operand[i];
	}
	return sum;
}


void _pc_applyResponse(uint8_t code, uint8_t value) {
	//walidacja
	if (value >=MESSAGE_BUFFER_SIZE || responseBuffer[value].count >(FRAME_BUFFER_SIZE-1)) {
		saf_eventBusSend_(EVENT_ERROR, ERROR_RESPONSE);
		return;
	}

	uint8_t commandCode = (code << 4) | (_pc_getControllCode(value) & 0x0F);
	if (commandCode == EOM || commandCode == PEOM) {
		saf_eventBusSend_(EVENT_RS_SEND, PEOM);
	}
	saf_eventBusSend_(EVENT_RS_SEND, commandCode);
	for (uint8_t i=0; i<responseBuffer[value].count; i++) {
		uint8_t tosend = responseBuffer[value].operand[i];
		if (tosend == EOM || tosend == PEOM) {
			saf_eventBusSend_(EVENT_RS_SEND, PEOM);
		}
		saf_eventBusSend_(EVENT_RS_SEND, tosend);
	}
	saf_eventBusSend_(EVENT_RS_SEND, EOM);
}

uint8_t pc_getMessageIndex() {
	uint8_t result = _pc_requestIndex;
	_pc_requestIndex++;
	if (_pc_requestIndex>=MESSAGE_BUFFER_SIZE) {
		_pc_requestIndex=0;
	}
	return result;
}
