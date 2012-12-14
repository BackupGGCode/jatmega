package pl.mazon.jatmega;

public class ConvertHelper {

	public static String byteToHex(byte b) {
		return String.format("%02X", b);
	}
	
	public static String byteArrayToHex(byte[] byteArray) {
		StringBuffer result = new StringBuffer();
		for (byte b:byteArray) {
		    result.append(String.format("%02X ", b));
		}
		return result.toString();
	}

	public static String ByteArrayToHex(Byte[] byteArray) {
		StringBuffer result = new StringBuffer();
		for (Byte b:byteArray) {
		    result.append(String.format("%02X ", b));
		}
		return result.toString();
	}

	public static String ByteArrayToDec(Byte[] byteArray) {
		StringBuffer result = new StringBuffer();
		for (Byte b:byteArray) {
		    result.append(" " + b);
		}
		return result.toString();
	}
}
