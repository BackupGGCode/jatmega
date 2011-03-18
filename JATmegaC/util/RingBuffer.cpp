/*
 * RingBuffer.cpp
 *
 *  Created on: 2010-06-21
 *      Author: rmazon
 */

#include "RingBuffer.h"

RingBuffer::RingBuffer() {
	flush();
}


void RingBuffer::add(char c)
{
  int i = (head + 1) % BUFFOR_SIZE;

  buffer[head] = c;
  head = i;
  if (i == tail) {
	  tail = (tail + 1) % BUFFOR_SIZE;
  }
}

inline uint8_t RingBuffer::available()
{
  return (BUFFOR_SIZE + head - tail) % BUFFOR_SIZE;
}

char RingBuffer::get()
{
  // if the head isn't ahead of the tail, we don't have any characters
  if (head == tail) {
    return RING_EMPTY;
  } else {
    unsigned char c = buffer[tail];
    tail = (tail + 1) % BUFFOR_SIZE;
    return c;
  }
}

void RingBuffer::flush()
{
  // don't reverse this or there may be problems if the RX interrupt
  // occurs after reading the value of rx_buffer_head but before writing
  // the value to rx_buffer_tail; the previous value of rx_buffer_head
  // may be written to rx_buffer_tail, making it appear as if the buffer
  // don't reverse this or there may be problems if the RX interrupt
  // occurs after reading the value of rx_buffer_head but before writing
  // the value to rx_buffer_tail; the previous value of rx_buffer_head
  // may be written to rx_buffer_tail, making it appear as if the buffer
  // were full, not empty.
  head = tail;
}
