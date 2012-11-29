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

int main() {

	saf_init();
	rs_init();

	//core event handler
	saf_addEventHandler(rs_onEvent);
	saf_addEventHandler(pc_onEnevt);

	//command event handler
	saf_addEventHandler(tc_apply);
	saf_addEventHandler(mc16_apply);

	sei();
	while(1) {
		saf_process();
		sleep_mode();
	}

}


