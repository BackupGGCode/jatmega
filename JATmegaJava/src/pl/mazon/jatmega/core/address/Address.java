package pl.mazon.jatmega.core.address;

public class Address<T> {
	
	private int address;
	
	public Address(int address) {
		this.address = address;
	}
	
	public int getAddress() {
		return address;
	}
	
	@Override
	public String toString() {
		return Integer.valueOf(address).toString();
	}

	public int intValue() {
		return address;
	}
	
	public byte byteValue() {
		return (byte) address;
	}
}
