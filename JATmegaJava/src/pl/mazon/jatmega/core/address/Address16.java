package pl.mazon.jatmega.core.address;

public class Address16 implements Address<Short> {

	private Short address;
	
	public Address16(int address) {
		this.address = new Short((short)address);
	}
	
	public Short getAddress() {
		return address;
	}
	
	@Override
	public String toString() {
		return address.toString();
	}

	@Override
	public int intValue() {
		return address.intValue();
	}
}
