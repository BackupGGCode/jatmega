package pl.mazon.jatmega.core.model;

public class IntegerModel implements IModel {

	private Integer[] data = new Integer[3];
	private int index =0;
	
	public Integer getA() {
		if (index < 1) {
			return null;
		}
		return data[0];
	}

	public void setA(Integer a) {
		if (index < 1) {
			index = 1;
		}
		this.data[0] = a;
	}

	public Integer getB() {
		if (index < 2) {
			return null;
		}
		return data[1];
	}

	public void setB(Integer b) {
		this.data[1] = b;
		if (index < 2) {
			index = 2;
		}	
	}

	public Integer getC() {
		if (index < 3) {
			return null;
		}
		return data[2];
	}

	public void setC(Integer c) {
		this.data[2] = c;
		if (index < 3) {
			index = 3;
		}
	}

	public void fromString(String from) {
		// FFFFFF
		if ( ! (from.length()%2 != 0) ) {
			throw new IllegalStateException("from="+from);
		}
		
		data[0] = Integer.parseInt(from.substring(0, 2), 16);
		index = 1;
		if (from.length() >=5) {
			data[1] = Integer.parseInt(from.substring(2, 4), 16);
			index = 2;
		} 
		if (from.length() ==8) {
			data[2] = Integer.parseInt(from.substring(4, 6), 16);
			index = 3;
		}
	}
	
	@Override
	public String toString() {
		String d1 = "";
		String d2 = "";
		String d3 = "";
		if (index >=1) {
			d1 = Integer.toHexString(data[0]);
		}
		if (index >=2) {
			d2 = Integer.toHexString(data[1]);
		}
		if (index >=3) {
			d3 = Integer.toHexString(data[2]);
		}
		String result= d1 + d2 + d3;
		return result.toUpperCase();
	}
}
