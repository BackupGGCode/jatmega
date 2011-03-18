/*
 * RSController.h
 *
 *  Created on: 2010-06-18
 *      Author: rmazon
 */


#include <avr/io.h>
#include <avr/interrupt.h>
#include "util/RingBuffer.h"
#include "util/Listener.h"

#define BAUDRATE 19200
#define EOL	((char)13)

//calculate UBRR value
#define UBRRVAL ((F_CPU/(BAUDRATE*16UL))-1)

#ifndef RSCONTROLLER_H_
#define RSCONTROLLER_H_

class RSController : public Listener {
public:
	RSController();
	void usartInit(unsigned int baud);
	void send(char* string);
	void sendLine(char* string);
	void onRx();
	void onTx();
	char* getLine();

private:
	RingBuffer sendRingBuffer;
	RingBuffer receiveRingBuffer;
};

#endif /* RSCONTROLLER_H_ */
