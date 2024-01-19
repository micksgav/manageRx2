package utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class Encrypt {

	private String Key;
	
	
	public Encrypt(String key) {
		this.Key = key;
	}
	
	public String encrypt(String plainText) {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(this.Key);
            SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public String decrypt(String encryptedText) {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(this.Key);
            SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));

            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	
	/*-----  Static Methods  -----*/
	
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
	
	public static String generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");//get instance of AES algorithm
            keyGenerator.init(256);//set desired key size
            SecretKey secretKey = keyGenerator.generateKey();//generate the key from the KeyGenerator
            byte[] encodedKey = secretKey.getEncoded();//get the raw byte key data 
            return Base64.getEncoder().encodeToString(encodedKey);//encode to base 64 for ease of handling 
        } catch (NoSuchAlgorithmException e) {
            //e.printStackTrace();
        	//log stack trace
            return null;
        }
    }
	
}
