/*
 * ProtocolTarget.h
 *
 *  Created on: 17-03-2011
 *      Author: radomir.mazon
 */

#ifndef PROTOCOLTARGET_H_
#define PROTOCOLTARGET_H_

class ICommand {
public:
	virtual void apply(char* request, char* response)=0;
	virtual char getTargetName()=0;
};

#endif /* PROTOCOLTARGET_H_ */
