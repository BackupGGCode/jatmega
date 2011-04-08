/*
 * IntegerModel.h
 *
 *  Created on: 06-04-2011
 *      Author: radomir.mazon
 */

#ifndef INTEGERMODEL_H_
#define INTEGERMODEL_H_

#include "IModel.h"

class IntegerModel : public  IModel {
public:
	IntegerModel();
	char* toString(char* p);
	void fromString(char* c);
	void setA(int a);
	void setB(int b);
	void setC(int c);
	int getA();
	int getB();
	int getC();
private:
	int offset;
	int tab[3];
};

#endif /* INTEGERMODEL_H_ */
