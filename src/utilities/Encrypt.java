/**
 ***********************************************
 * @Author: Brayden Johnson
 * @Creation date: December 19, 2023
 * @Modification date: January 10, 2023
 * @Description: This object is used for hashing Strings with sha256 and sha512 and symmetric encryption and decryption with a key generator
 ***********************************************
 */

package utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class Encrypt {
	
	private String Key;//encryption & decryption key 
	
	
	public Encrypt(String key) {
		this.Key = key;
	}

	/**
	 * @author Brayden Johnson
	 * @date December 20 2023
	 * @param decryptedstring
	 * @return encryptedtext
	 */
	public String encrypt(String plainText) {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(this.Key);
            SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

            Cipher cipher = Cipher.getInstance("AES");//get instance of cipher with AES
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());//encrypt string to bytes

            return Base64.getEncoder().encodeToString(encryptedBytes);//encode the bytes to string with base 64
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	/**
	 * @author Brayden Johnson
	 * @date December 20 2023
	 * @param encryptedText
	 * @return planetext
	 */
	public String decrypt(String encryptedText) {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(this.Key);
            SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

            Cipher cipher = Cipher.getInstance("AES");//get instance of cipher with AES
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));//decode the base 64 string and decrypt

            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	
	/*-----  Static Methods  -----*/
	/**
	 * @author Brayden Johnson
	 * @date December 19 2023
	 * @param encryptedText
	 * @return sha256hash
	 */
	public static String SHA256(String data) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = md.digest(data.getBytes());

	        StringBuilder hashStringBuilder = new StringBuilder();
	        for (byte b : hashBytes) {
	            hashStringBuilder.append(String.format("%02x", b));
	        }

	        return hashStringBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
			
			//log stack trace
			//e.printStackTrace();
		}
	}
	
	/**
	 * @author Brayden Johnson
	 * @date December 21 2023
	 * @param String
	 * @return sha512hash
	 */
	public static String SHA512(String data) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-512");
			byte[] hashBytes = md.digest(data.getBytes());

	        StringBuilder hashStringBuilder = new StringBuilder();
	        for (byte b : hashBytes) {
	            hashStringBuilder.append(String.format("%02x", b));
	        }

	        return hashStringBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
			
			//log stack trace
			//e.printStackTrace();
		}
	}
	/**
	 * @author Brayden Johnson
	 * @date December 20 2023
	 * @return key
	 */
	public static String generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");//get instance of key generator with AES algorithm 
            keyGenerator.init(2048);//set desired key size
            SecretKey secretKey = keyGenerator.generateKey();//generate the key from the KeyGenerator
            byte[] encodedKey = secretKey.getEncoded();//get the raw byte key data 
            return Base64.getEncoder().encodeToString(encodedKey);//encode to base 64 for ease of handling 
        } catch (NoSuchAlgorithmException e) {
        	//log stack trace
            return null;
        }
    }
	
}
