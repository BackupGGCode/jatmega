/*
 * EventControler.h
 *
 *  Created on: 2010-06-13
 *      Author: rmazon
 */

#include <stdint.h>

#ifndef EVENTCONTROLLER_H_
#define EVENTCONTROLLER_H_

class EventController  {
public:
	EventController();

	/**
	 * Cyklicznie wywoływana metoda
	 * w procesurze obsługi timera
	 * lub w głównej pętli.
	 */
	void onTick();

	/**
	 * Determinuje czy wystąpiło zdarzenie
	 */
	virtual uint8_t getEvent(){return 1;};

	/**
	 * Po ilu zdarzeniach ma wystąpić multievent
	 */
	virtual uint8_t getMultiEventCount(){return 16;}

	/**
	 * Pojedyncze wyktycie zdarzenia
	 */
	virtual void onOneEvent()=0;

	/**
	 * Wyktycie dlugiego zdarzenia
	 */
	virtual void onLongEvent()=0;

	/**
	 * Wykrycie konca wystepienia zdarzenia
	 */
	virtual void onEndLongEvent()=0;

	/**
	 * Wyktycie multi zdarzenia
	 */
	virtual void onMultiEvent(){}


private:
	uint8_t counter;
	uint8_t lastEvent;
	uint8_t longCounter;
	uint8_t multiEventCount;
	uint8_t multiEventTimer;
	void reset();
	void multiEventOneEvent();
	void multiEventEvent();
};

#endif /* EVENTCONTROLLER_H_ */
