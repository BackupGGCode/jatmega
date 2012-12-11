package pl.mazon.jatmega.core.model;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class WordModel extends ArrayList<Integer> implements IModel {

	public void fromString(String from) {
		if (from.length() % 4 != 0) {
			throw new IllegalStateException("from=" + from);
		}

		removeAll(this);

		for (int i = 0; i < from.length(); i = i + 4) {
			add(Integer.parseInt(from.substring(i, i + 4), 16));
		}
	}

	@Override
	public String toString() {
		String result = "";
		for (Integer word : this) {
			String d = Integer.toHexString(word);
			for (int i=0; i<=(4-d.length()) ;i++) {
				d = "0" + d;
			}
			d = d.substring(d.length() - 4, d.length());
			
			result += d;
		}
		return result;
	}

	@Override
	public byte[] serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deserialize(byte[] m) {
		// TODO Auto-generated method stub
		
	}
}