/*
 * main.c
 *
 *  Created on: 28-11-2012
 *      Author: rma
 */

#include "saf2core.h"
#include "adds/rscom.h"
#include "ProtocolController.h"
#include "command/MemoryCommand_16.h"
#include "command/TestCommand.h"


void onErrorEvent(saf_Event e) {
	if (e.code == EVENT_ERROR && e.value == ERROR_INDEX_FRAME) {
		saf_eventBusSend_(EVENT_RS_SEND, 'I');
	}
	if (e.code == EVENT_ERROR && e.value == ERROR_RESPONSE) {
		saf_eventBusSend_(EVENT_RS_SEND, 'R');
	}
	if (e.code==EVENT_FRAME_OVERFLOW) {
		saf_eventBusSend_(EVENT_RS_SEND, 'O');
		saf_eventBusSend_(EVENT_RS_SEND, 'V');
		saf_eventBusSend_(EVENT_RS_SEND, 'F');
		saf_eventBusSend_(EVENT_RS_SEND, e.value);
	}
}

uint8_t s=1;
uint8_t d=0;
void onMaja(saf_Event e) {
	if (e.code == EVENT_SAFTICK && e.value == 0) {
		DDRC = 0xff;
		DDRD = 0xff;
		if (d==0) {
			s =s<<1;
			PORTC = ~s;
			PORTD = ~s;
		} else {
			s = s>>1;
			PORTC = ~s;
			PORTD = ~s;
		}
		if (s == 1) {
			d=0;
		}
		if (s == 0x80) {
			d=1;
		}
	}
}

int main() {

	saf_init();

	//core event handler
	saf_addEventHandler(rs_onEvent);
	saf_addEventHandler(pc_onEnevt);
	saf_addEventHandler(onErrorEvent);

	//command event handler
	saf_addEventHandler(tc_apply);
	saf_addEventHandler(mc16_apply);

	saf_addEventHandler(onMaja);
	sei();
	while(1) {
		saf_process();
	}

}

