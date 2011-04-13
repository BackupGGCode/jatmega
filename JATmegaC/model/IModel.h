/*
 * IModel.h
 *
 *  Created on: 06-04-2011
 *      Author: radomir.mazon
 */

#ifndef IMODEL_H_
#define IMODEL_H_

#include <avr/io.h>

class IModel {
public:
	virtual char* toString(char* p)=0;
	virtual void fromString(char* c)=0;
protected:
	uint8_t atoi2(char* temp);
	void addZero(char* temp);
private:
	uint8_t convertHalfHex(uint8_t temp);

};

#endif /* IMODEL_H_ */
