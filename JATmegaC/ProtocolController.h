/*
 * ProtocolContriller.h
 *
 *  Created on: 12-03-2011
 *      Author: radomir.mazon
 */

#ifndef PROTOCOLCONTROLLER_H_
#define PROTOCOLCONTROLLER_H_

#include "util/ListenerAware.h"
#include "ProtocolTarget.h"

#define TARGET_NUM 8

class ProtocolController : public ListenerAware{
public:
	ProtocolController();
	void onEvent(void* owner);
	void registerProtocolTarget(ProtocolTarget* taget){targetTab[tabIndex++] = taget;}
private:
	char* removeProtocolFrame(char* message);
	ProtocolTarget* targetTab[TARGET_NUM];
	int tabIndex;
};

#endif /* PROTOCOLCONTROLLER_H_ */
