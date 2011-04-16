/*
 * MemoryCommand.cpp
 *
 *  Created on: 20-03-2011
 *      Author: radomir.mazon
 *
 *     bajt		opis
 *     0		Adres RAM
 *     1		Wartosc
 *     2		Operacja
 *
 *     rodzaj operacji:
 *     S		=
 *     A		AND
 *     O		OR
 *
 *     W odpowiedzi zawartosc zmienionego adresu pamięci
 */

#include "MemoryCommand.h"
#include "../model/ByteModel.h"

void MemoryCommand::apply(char* request, char* response) {
	ByteModel model;
	model.fromString(request);
	uint8_t* address = (uint8_t*)model.get(0);
	uint8_t  value = model.get(1);
	uint8_t  operation = model.get(2);
	if (operation == 'S') {
		*address = value;
	} else if (operation == 'A') {
		*address = *address & value;
	} else if (operation == 'O') {
		*address = *address | value;
	}
	ByteModel resp;
	resp.add(*address);
	resp.toString(response);
}
