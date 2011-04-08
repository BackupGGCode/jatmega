/*
 * MemoryCommand.h
 *
 *	Podstawowe operacje zapisu pamięci.
 *
 *  Created on: 20-03-2011
 *      Author: radomir.mazon
 */

#ifndef MEMORYCOMMAND_H_
#define MEMORYCOMMAND_H_
#include "../ProtocolTarget.h"

class MemoryCommand : public ProtocolTarget {
	void serialize(char* message, char* result);
	char getTargetName(){return 'M';}
};

#endif /* MEMORYCOMMAND_H_ */
