/*
 * Listener.h
 *
 *  Created on: 08-03-2011
 *      Author: radomir.mazon
 */

#ifndef LISTENER_H_
#define LISTENER_H_

#include "ListenerAware.h"
#include <stdint.h>

class Listener {
public:
	Listener();
	void removeListener(ListenerAware *fp);
	void addListener(ListenerAware *fp);
protected:
	void fireEvent(void* event);
private:
	uint8_t listener_count;
	/* array of function pointers */
	ListenerAware *listener_list[8];
};

#endif /* LISTENER_H_ */
