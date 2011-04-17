/*
 * IntegerModel.h
 *
 *  Created on: 06-04-2011
 *      Author: radomir.mazon
 */

#ifndef INTEGERMODEL_H_
#define INTEGERMODEL_H_

#include "IModel.h"
#define MAX_SIZE 10

class ByteModel : public  IModel {
public:
	ByteModel();
	char* toString(char* p);
	void fromString(char* c);
	void add(uint8_t a);
	uint8_t get(uint8_t index);
	uint8_t getSize();
private:
	uint8_t size;
	uint8_t data[MAX_SIZE];
};

#endif /* INTEGERMODEL_H_ */
