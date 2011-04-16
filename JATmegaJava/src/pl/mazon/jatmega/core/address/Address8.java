package pl.mazon.jatmega.core.address;

public class Address8 implements Address<Byte> {

	private Byte address;
	
	public Address8(int address) {
		this.address = new Byte((byte)address);
	}
	
	public Byte getAddress() {
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
