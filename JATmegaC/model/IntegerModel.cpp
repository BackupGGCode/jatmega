/*
 * IntegerModel.cpp
 *
 *  Created on: 06-04-2011
 *      Author: radomir.mazon
 */

#include "IntegerModel.h"
#include <stdlib.h>


	IntegerModel::IntegerModel(){
		offset = 0;
	}

	char* IntegerModel::toString(char* result) {
		char temp[3] ={0};
		if (offset == 0) {
			result[0] = 0;
			return (char*)result;
		}

		if (offset >= 1) {
			itoa(tab[0], temp, 16);
			addZero(temp);
			result[0] = temp[0];
			result[1] = temp[1];
		}
		result[2] = 0;

		if (offset >= 2) {
			itoa(tab[1], temp, 16);
			addZero(temp);
			result[2] = temp[0];
			result[3] = temp[1];
		}
		result[4] = 0;

		if (offset >= 3) {
			itoa(tab[2], temp, 16);
			addZero(temp);
			result[4] = temp[0];
			result[5] = temp[1];
		}
		result[6] = 0;
		return result;
	}

	void IntegerModel::fromString(char* c) {
		int i=0;
		while(c[i] != 0) i++;
		offset = 2;
		char temp[3];
		temp[2] = 0;
		if (offset >=1) {
			temp[0] = c[0];
			temp[1] = c[1];
			tab[0] = atoi2(temp);
		}
		if (offset >=2) {
			temp[0] = c[2];
			temp[1] = c[3];
			tab[1] = atoi2(temp);
		}
		if (offset >=3) {
			temp[0] = c[4];
			temp[1] = c[5];
			tab[2] = atoi2(temp);
		}
	}

	void IntegerModel::setA(int a) {
		if (offset < 1) {
			offset = 1;
		}
		tab[0] = a;
	}

	void IntegerModel::setB(int b) {
		if (offset <2) {
			offset = 2;
		}
		tab[1] = b;
	}

	void IntegerModel::setC(int c) {
		offset = 3;
		tab[2] = c;
	}

	int IntegerModel::getA() {
		return tab[0];
	}

	int IntegerModel::getB() {
		return tab[1];
	}

	int IntegerModel::getC() {
		return tab[2];
	}
