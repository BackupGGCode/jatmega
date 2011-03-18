/*
 * RSController.cpp
 *
 *  Created on: 2010-06-18
 *      Author: rmazon
 *
 */

#include "RSController_168.h"
#include <util/delay.h>
#include <string.h>

extern RSController rs;

SIGNAL(USART_TX_vect) {
	rs.onTx();
}

SIGNAL(USART_RX_vect) {
	rs.onRx();
}


RSController::RSController() {
	usartInit(UBRRVAL);
}

void RSController::usartInit(unsigned int baud)
{
	//Set baud rate
	UBRR0 = baud;

	//Set data frame format: asynchronous mode,no parity, 1 stop bit, 8 bit size
	//Parzysto�� UPM01 =1 (wy�aczona 0) UPM00: even = 0 odd = 1
	UCSR0C=(0<<UMSEL01)|(0<<UMSEL00)|(0<<UPM01)|(0<<UPM00)|(0<<USBS0)|(0<<UCSZ02)|(1<<UCSZ01)|(1<<UCSZ00);

	//Enable Transmitter and Receiver and Interrupt on receive complete
	UCSR0B=(1<<RXEN0)|(1<<TXEN0)|(1<<RXCIE0) | (1<<TXCIE0) ;
}

void RSController::send(char* string) {
	while(*string) {
		sendRingBuffer.add(*string);
		string++;
	}
	onTx();
}

void RSController::sendLine(char* string) {
	send(string);
	sendRingBuffer.add((unsigned char)EOL);
	sendRingBuffer.add('\n');
	onTx();
}

/**
 * Obs�uga przerwania USART_RX
 */
void RSController::onRx() {
	if (bit_is_clear(UCSR0A, RXC0)) {
		return;
	}
	char data = UDR0;
	receiveRingBuffer.add(data);
	if (data == EOL) {
		char line[BUFFOR_SIZE];
		int index = 0;
		char c = 0;
		do {
			c = receiveRingBuffer.get();
			line[index++] = c;
		} while(!(c == RING_EMPTY || c == EOL));
		line[index] = 0;

		fireEvent(&line);
	}

	//echo
	//sendRingBuffer.add(data);
	//onTx();
}

void RSController::onTx() {
	if (bit_is_clear(UCSR0A, UDRE0)) {
		return;
	}
	char c = sendRingBuffer.get();
	if (c == RING_EMPTY) {
		return;

	}
	UDR0 = c;
}

