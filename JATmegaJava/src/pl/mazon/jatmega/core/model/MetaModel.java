package pl.mazon.jatmega.core.model;

/**
 * 2012-12-03
 * @author radomir.mazon
 * Meta model danych dla jatmega 2.0
 * Odpowiada modelowi przyjetemu po stronie odbiorcy (ATMEGA)
 * Zawietac moze od 0 do 3 bajtow wiadomosci.
 */

public class MetaModel implements IModel{
	
	private byte[] data;
	
	public MetaModel() {
		data = null;
	}
	
	public void add(byte x) {
		if (data == null) {
			data = new byte[1];
			data[0] = x;
		} else {
			byte[] newData = new byte[data.length +1];
			for (int i=0; i<data.length;i++) {
				newData[i] = data[i];
			}
			newData[data.length] = x;
			data = newData;
		}
	}
	
	public byte getOperandA() {
		return data[0];
	}
	
	public byte getOperandB() {
		return data[1];
	}
	
	public byte getOperandC() {
		return data[2];
	}
	
	@Override
	public byte[] serialize() {
		return data;
	}

	@Override
	public void deserialize(byte[] m) {
		data = m;
	}
}