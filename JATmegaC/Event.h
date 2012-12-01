//Budowa event:
//EVENT_doKogo_co

#define EVENT_NULL			0x00
#define EVENT_INIT			0x01
#define EVENT_SAFTICK		0x02
#define EVENT_RS_SEND		0x03
#define EVENT_RS_RECEIVE	0x04
#define EVENT_BUTTON_DOWN	0x05
#define EVENT_BUTTON_UP		0x06
#define EVENT_ERROR			0x07
#define EVENT_FRAME_OVERFLOW 0x08



//Zarezerwowane dla command code 0xE0 - 0xFF
#define REQUEST_GROUP	0xF0
#define RESPONSE_GROUP	0xE0

#define COMMAND_TEST		0xF0
#define COMMAND_MEMORY_16	0xF1
#define COMMAND_MEMORY_8	0xF2
#define COMMAND_AND_16		0xF3
#define COMMAND_AND_8		0xF4
#define COMMAND_OR_16		0xF5
#define COMMAND_OR_8		0xF6
#define COMMAND_7			0xF7
#define COMMAND_8			0xF8
#define COMMAND_9			0xF9
#define COMMAND_A			0xFA
#define COMMAND_B			0xFB
#define COMMAND_C			0xFC
#define COMMAND_D			0xFD
#define COMMAND_E			0xFE
#define COMMAND_STREAM		0xFF

#define RESPONSE_TEST		0xE0
#define RESPONSE_MEMORY_16	0xE1
#define RESPONSE_MEMORY_8	0xE2
#define RESPONSE_AND_16		0xE3
#define RESPONSE_AND_8		0xE4
#define RESPONSE_OR_16		0xE5
#define RESPONSE_OR_8		0xE6
#define RESPONSE_7			0xE7
#define RESPONSE_8			0xE8
#define RESPONSE_9			0xE9
#define RESPONSE_A			0xEA
#define RESPONSE_B			0xEB
#define RESPONSE_C			0xEC
#define RESPONSE_D			0xED
#define RESPONSE_E			0xEE
#define RESPONSE_STREAM		0xEF
