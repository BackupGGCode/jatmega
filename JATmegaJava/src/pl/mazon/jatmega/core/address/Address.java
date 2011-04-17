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
		return new Integer(address).toString();
	}

	public int intValue() {
		return address;
	}
}
