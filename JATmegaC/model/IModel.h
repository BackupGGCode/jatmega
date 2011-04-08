/*
 * IModel.h
 *
 *  Created on: 06-04-2011
 *      Author: radomir.mazon
 */

#ifndef IMODEL_H_
#define IMODEL_H_

class IModel {
public:
	virtual char* toString(char* p)=0;
	virtual void fromString(char* c)=0;
protected:
	int atoi2(char* temp);
	void addZero(char* temp);
private:
	int convertHalfHex(char temp);

};

#endif /* IMODEL_H_ */
