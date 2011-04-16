/*
 * IModel.cpp
 *
 *  Created on: 06-04-2011
 *      Author: radomir.mazon
 */

#include "IModel.h"

	uint8_t IModel::atoi2(char* temp) {
		return ((convertHalfHex(temp[0]))<<4) | convertHalfHex(temp[1]);
	}

	/**
	 * Konwertuje wartosc ascii na 4 bity (0-f)
	 */
	uint8_t IModel::convertHalfHex(uint8_t temp) {
		if (temp >= 'a') {
			temp = temp - 0x20;
		}
		if (temp >= '9') {
			temp = temp - 7;
		}
		return temp - '0';
	}

	void IModel::addZero(char* temp) {
		if (temp[1] == 0) {
			temp[1] = temp[0];
			temp[0] = '0';
		}
	}
