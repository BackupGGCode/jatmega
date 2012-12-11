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
	if (e.code==EVENT_ERROR && e.value== ERROR_CONTROLL_CODE) {
		saf_eventBusSend_(EVENT_RS_SEND, 'S');
		saf_eventBusSend_(EVENT_RS_SEND, 'U');
		saf_eventBusSend_(EVENT_RS_SEND, 'M');
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

	while(1) {
		saf_process();
	}

}

