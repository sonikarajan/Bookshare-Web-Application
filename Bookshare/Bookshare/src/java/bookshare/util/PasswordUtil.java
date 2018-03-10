package bookshare.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Base64;


public class PasswordUtil {

	public static String hashPassword(String password)
			throws NoSuchAlgorithmException {
		//Algorithm which generates the hash key for the given password.
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.reset();
		md.update(password.getBytes());
		byte[] mdArray = md.digest();
		StringBuilder sb = new StringBuilder(mdArray.length * 2);
		for (byte b : mdArray) {
			int v = b & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString();
	}

	public static String creatSalt() {
		//Generates a random 256 bit Salt key
		Random r = new SecureRandom();
		byte[] saltBytes = new byte[32];
		r.nextBytes(saltBytes);
		return Base64.getEncoder().encodeToString(saltBytes);
	}

	public static String hashAndSaltPassword(String password,String salt)
			throws NoSuchAlgorithmException {
		//Generates Hashed Password for the given combination of password and salt
		return hashPassword(password + salt);
	}
}
