/*
 * IntegerModel.cpp
 *
 *  Created on: 06-04-2011
 *      Author: radomir.mazon
 */

#include "ByteModel.h"
#include <stdlib.h>
#include <string.h>


	ByteModel::ByteModel(){
		size = 0;
	}

	char* ByteModel::toString(char* result) {
		char temp[3] ={0};
		result[0] = 0;

		for (uint8_t i=0; i<size ;i++) {
			utoa(data[i], temp, 16);
			addZero(temp);
			strcat(result, temp);
		}
		return result;
	}

	void ByteModel::fromString(char* c) {
		size = 0;
		uint8_t cs = 0;
		char temp[3]={0};
		while(c[cs] != 0) cs++;
		for (uint8_t i=0; i<cs; i+=2) {
			temp[0] = c[i];
			temp[1] = c[i+1];
			data[size] = atoi2(temp);
			size++;
		}
	}

	void ByteModel::add(uint8_t a) {
		if (size == MAX_SIZE) {
			return;
		}
		data[size] = a;
		size++;
	}

	uint8_t ByteModel::get(uint8_t index) {
		if (size > index) {
			return data[index];
		}
		return 0;
	}
