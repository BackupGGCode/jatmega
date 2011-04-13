/*
 * Listener.cpp
 *
 *  Created on: 08-03-2011
 *      Author: radomir.mazon
 */

#include "Listener.h"
#include <stdint.h>

Listener::Listener() {
	listener_count = 0;
}

void Listener::removeListener(ListenerAware *fp)
{
  unsigned i;

  for( i = 0; i < listener_count; ++i )
  {
    if( listener_list[i] == fp )
    {
      listener_count--;
      listener_list[i] = listener_list[listener_count];
    }
  }
}

void Listener::addListener(ListenerAware* fp)
{
  removeListener( fp );
  listener_list[ listener_count++ ] = fp;
}

void Listener::fireEvent(void* event)
{
	uint8_t i = 0;
	for(; i < listener_count; i++ )
	{
		listener_list[i]->onEvent(event);
	}
}
