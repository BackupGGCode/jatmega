/*
 * ProtocolContriller.h
 *
 *  Created on: 12-03-2011
 *      Author: radomir.mazon
 */

#ifndef PROTOCOLCONTROLLER_H_
#define PROTOCOLCONTROLLER_H_

#include "util/ListenerAware.h"
#include "command/ICommand.h"
#include <stdio.h>

#define TARGET_NUM 8

class ProtocolController : public ListenerAware{
public:
	ProtocolController();
	void onEvent(void* owner);
	void registerProtocolTarget(ICommand* taget){targetTab[tabIndex++] = taget;}
private:
	void removeProtocolFrame(char* message);
	void addProtocolFrame(char* message, char targetName, char targetNum);
	ICommand* targetTab[TARGET_NUM];
	int tabIndex;
};

#endif /* PROTOCOLCONTROLLER_H_ */

