package data.as.a.service.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	public static String get32bit(String source) {
		
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] bytes = md.digest(source.getBytes());
		StringBuilder sb = new StringBuilder();
		for (byte b: bytes) {
			int i = b;
			if (i < 0) {
				i += 256;
			}
			if (i < 16) {
				sb.append("0");
			}
			sb.append(Integer.toHexString(i));
		}
		
		return sb.toString();
	}
	
	public static String get16bit(String source) {
		return get32bit(source).substring(8, 24);
	}
}
