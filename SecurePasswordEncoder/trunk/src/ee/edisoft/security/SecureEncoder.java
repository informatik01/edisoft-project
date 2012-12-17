package ee.edisoft.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class SecureEncoder {

	private static final String ALGORITHM = "SHA-256";
	
	private SecureEncoder() {}

	public static String binaryToHexString(byte[] bytes) {
		StringBuffer hex = new StringBuffer(bytes.length * 2);
		for (byte b: bytes) {
			int n = b & 0xFF;	// casting to integer to avoid problems with negative bytes
			if (n < 0x10) {
				hex.append("0");
			}
			hex.append(Integer.toHexString(n));
		}
		
		return hex.toString();
	}
	
	public static String generateSalt() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] saltBytes = new byte[32];
		secureRandom.nextBytes(saltBytes);
		
		return binaryToHexString(saltBytes);
	}
	
	public static String computeHash(String password, String salt) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(ALGORITHM);
		byte[] hash = md.digest((password + salt).getBytes());
		
		return binaryToHexString(hash);
	}
	
	public static String computeHash(String password) throws NoSuchAlgorithmException {
		String salt = generateSalt();
		
		return computeHash(password, salt);
	}
}
