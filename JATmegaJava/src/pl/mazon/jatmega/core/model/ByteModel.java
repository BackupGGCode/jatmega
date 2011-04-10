package pl.mazon.jatmega.core.model;

import java.util.ArrayList;

/**
 * Dane przechowyjemy w Integer, poniważ byte to zakres od -127 do +128
 * natomiast my potrzebujemy zakres od 0 do 255.
 * @author radomir.mazon
 *
 */

@SuppressWarnings("serial")
public class ByteModel extends ArrayList<Integer> implements IModel {
	
	public void fromString(String from) {
		// FFAAFF
		if (from.length()%2 != 0) {
			throw new IllegalStateException("from="+from);
		}
		
		removeAll(this);
		
		for (int i=0; i<from.length() ;i=i+2) {
			add(Integer.parseInt(from.substring(i, i+2), 16));
		}
	}
	
	@Override
	public String toString() {
		String result = "";
		for (Integer bytee : this) {
			String d = Integer.toHexString(bytee);
			if (d.length() < 2) {
				d = "0" + d;
			} else {
				d = d.substring(d.length()-2, d.length());
			}
			result += d;
		}
		return result;
	}
}
