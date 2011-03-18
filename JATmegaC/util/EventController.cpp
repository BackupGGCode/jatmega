/*
 * EventControler.cpp
 *
 *  Created on: 2010-06-13
 *      Author: rmazon
 */

#include "EventController.h"

#define MULTI_EVENT_TIMEOUT		200

EventController::EventController() {
	reset();
	multiEventCount = 0;
}

inline void EventController::reset() {
	lastEvent = 0;
	counter = 0;
	longCounter = 0;
}

void EventController::onTick() {
	if ( getEvent() == 1) {
		if (lastEvent == 0) {
			reset();
			lastEvent = 1;
		}

		if (counter == 30) {
			longCounter = 1;
		}

		if (longCounter == 1) {
			onLongEvent();
		}
		counter++;
	}
	else
	{
		if (counter > 0) {
			if (longCounter == 0) {
				onOneEvent();
				multiEventOneEvent();
			} else {
				onEndLongEvent();
			}
		}

		reset();
	}
	multiEventEvent();
}

void EventController::multiEventOneEvent() {
	multiEventCount++;
	if (multiEventCount == getMultiEventCount() && multiEventTimer < MULTI_EVENT_TIMEOUT) {
		onMultiEvent();
		multiEventCount = 0;
		multiEventTimer = 0;
	}

}

void EventController::multiEventEvent() {
	if (multiEventCount > 0) {
		multiEventTimer++;
	}
	if (multiEventTimer > MULTI_EVENT_TIMEOUT) {
		multiEventCount = 0;
		multiEventTimer = 0;
	}
}
