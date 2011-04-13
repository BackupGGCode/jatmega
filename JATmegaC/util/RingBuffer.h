/*
 * RingBuffer.h
 *
 *  Created on: 2010-06-21
 *      Author: rmazon
 */
#include <avr/io.h>

#define BUFFOR_SIZE 128
#define RING_EMPTY ((char)-1)

#ifndef RINGBUFFER_H_
#define RINGBUFFER_H_

class RingBuffer {
public:
	RingBuffer();
	void add(char c);
	char get();
	uint8_t available();
	void flush();
private:
	//unsigned char buffer[];
	unsigned char buffer[BUFFOR_SIZE];
	uint8_t head;
	uint8_t tail;
};

#endif /* RINGBUFFER_H_ */
