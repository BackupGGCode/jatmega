package pl.mazon.jatmega.core.model;

/**
 * 
 * @author radomir.mazon
 *
 */

public interface IModel {
	byte[] serialize();
	void deserialize(byte[] m);
}
