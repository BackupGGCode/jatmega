/*
 * ProtocolTarget.h
 *
 *  Created on: 17-03-2011
 *      Author: radomir.mazon
 */

#ifndef PROTOCOLTARGET_H_
#define PROTOCOLTARGET_H_

class ProtocolTarget {
public:
	virtual void serialize(char* message, char* result)=0;
	virtual char getTargetName()=0;
};

#endif /* PROTOCOLTARGET_H_ */
