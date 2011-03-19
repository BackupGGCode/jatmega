package pl.mazon.jatmega.core;

import java.util.ArrayList;
import java.util.List;

import pl.mazon.jatmega.core.bus.IBus;
import pl.mazon.jatmega.core.command.ICommand;

/**
 * Menadzer jest wątkiem wysyłającym polecenia do ATMEGA
 * Obsługuje komunikacę szeregowo. Po wysłaniu pytania
 * czeka na odpowiedź.
 * @author radomir.mazon
 *
 */

public class ProtocolManager extends Thread {

	private static ProtocolManager instance;
	
	private List<ICommand> protocolList;
	
	private IBus bus;
	
	private ProtocolManager() {		
		super("ProtocolManager");
		protocolList = new ArrayList<ICommand>();
		start();
	}
	
	public static ProtocolManager getInstance() {
		if (instance == null) {
			instance = new ProtocolManager();
		}
		return instance;
	}
	
	@Override
	public void run() {
		int i=0; i++;
	}

	public void setBus(IBus bus) {
		this.bus = bus;
	}

	
	/**
	 * Metoda dodaje protokuł do listy wykonywanych przez wątek ProtocolManager
	 * Protokół umie się serializować i deserializować. OnFailure wykonywane jest
	 * gdy nasąpi timeout odpowiedzi. Odpowiedź jest potwierdzeniem. Niesie też informację zwrotną,
	 * która jest przekazywana w onSuccess jako parametr.
	 * @param testProtocol
	 */
	public void apply(ICommand protocol) {
		protocolList.add(protocol);
		notify();
	}
}