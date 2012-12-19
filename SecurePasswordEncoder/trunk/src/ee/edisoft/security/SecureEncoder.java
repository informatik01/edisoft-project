package ee.edisoft.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * The SecureEncoder class contains useful utility methods
 * that help securely encode data using a hash algorithm.<br />
 * One of the usage scenarios is encoding user passwords.
 * 
 */
public final class SecureEncoder {

	private static final String ALGORITHM = "SHA-256";
	
	private SecureEncoder() {}

	/**
	 * Produces hexadecimal representation of the supplied binary data.
	 * 
	 * @param bytes The array of bytes to represent as hexadecimal string
	 * @return hexadecimal representation of the supplied byte array argument
	 */
	public static String binaryToHexString(byte[] bytes) {
		StringBuffer hex = new StringBuffer(bytes.length * 2);
		for (byte b: bytes) {
			int n = b & 0xFF;	// casting to integer to avoid problems with sign
			if (n < 0x10) {
				hex.append("0");
			}
			hex.append(Integer.toHexString(n));
		}
		
		return hex.toString();
	}
	
	/**
	 * Generates cryptographically strong random number to be used as a salt.
	 * 
	 * @return hexadecimal string containing generated salt
	 */
	public static String generateSalt() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] saltBytes = new byte[32];
		secureRandom.nextBytes(saltBytes);
		
		return binaryToHexString(saltBytes);
	}
	
	/**
	 * Computes hash using the supplied data and salt.
	 * 
	 * @param data The data to compute hash for
	 * @param salt The salt to use along with the data when hashing
	 * @return The salted data hash
	 * @throws NoSuchAlgorithmException if the algorithm used while hashing could not be found
	 */
	public static String computeHash(String data, String salt) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(ALGORITHM);
		byte[] hash = md.digest((data + salt).getBytes());
		
		return binaryToHexString(hash);
	}
	
	/**
	 * Computes hash using the supplied data and some random salt.
	 * The salt will be randomly generated using {@link #generateSalt()} method.
	 * 
	 * @param data The data to compute hash for
	 * @return The salted data hash
	 * @throws NoSuchAlgorithmException if the algorithm used while hashing could not be found
	 */
	public static String computeHash(String data) throws NoSuchAlgorithmException {
		String salt = generateSalt();
		
		return computeHash(data, salt);
	}
}
