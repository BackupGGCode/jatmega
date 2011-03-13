package pl.mazon.jatmega.core.bus;

import pl.mazon.jatmega.core.bus.event.IBusEvent;

public interface IBusEventCallback {

	void eventCallback(IBusEvent event);
}
