
package utility;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtility {

	public PasswordUtility() {
		
	}
	
	public static byte[] getSalt() throws NoSuchAlgorithmException{
		SecureRandom sRandom=SecureRandom.getInstance("SHA1PRNG");
		byte[] salt=new byte[8];
		sRandom.nextBytes(salt);
		return salt;
	}
	
	 static String toHex(byte[] array){
		BigInteger bigInteger=new BigInteger(1,array);
		String hexString=bigInteger.toString(16);
		return hexString;
	}
	public static String generateStorngPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		int iterations = 1000;
		char[] chars = password.toCharArray();
		byte[] salt = getSalt();
		PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] hash = skf.generateSecret(spec).getEncoded();
		return iterations + ":" + toHex(salt) + ":" + toHex(hash);
	}
	public static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		String[] parts = storedPassword.split(":");
		int iterations = Integer.parseInt(parts[0]);
		byte[] salt = fromHex(parts[1]);
		byte[] hash = fromHex(parts[2]);
		
		PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] testHash = skf.generateSecret(spec).getEncoded();
		
		int diff = hash.length ^ testHash.length;
		for(int i = 0; i < hash.length && i < testHash.length; i++)
		{
			diff |= hash[i] ^ testHash[i];
		}
		return diff == 0;
	}
	private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
	{
		byte[] bytes = new byte[hex.length() / 2];
		for(int i = 0; i<bytes.length ;i++)
		{
			bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException{
	    String pwdString=generateStorngPasswordHash("SIMONEsdsdsd88");
		System.out.println(pwdString);
		System.out.println(pwdString.length());
		System.out.println(validatePassword("SIMONEsdsdsd88", pwdString));
		
	}

}
