/*
 * ListenerAware.h
 *
 *  Created on: 13-03-2011
 *      Author: radomir.mazon
 */

#ifndef LISTENERAWARE_H_
#define LISTENERAWARE_H_

class ListenerAware {
public:
	virtual void onEvent(void* event)=0;
};

#endif /* LISTENERAWARE_H_ */
