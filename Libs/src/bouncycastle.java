import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.crypto.*;
import java.security.*;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class bouncycastle {
	
	private static Key key;
	
	public static void generateKey(int keyBitSize) throws GeneralSecurityException, IOException {
		
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		// initialize the keyGenerator
		SecureRandom secureRandom = new SecureRandom();
		generator.init(keyBitSize, secureRandom);
		// generate a key for symmetric encryption
		key = generator.generateKey();
	}
	
	public static byte[] encrypt(byte[] plainText) throws GeneralSecurityException {
		//The Cipher class represents a cryptographic algorithm 
		//In this case it uses the AES encryption algorithm internally.
		Cipher cipher = Cipher.getInstance("AES");
		// initialize the cipher
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encryptedOutput = cipher.doFinal(plainText);
		return encryptedOutput;
	}
	
	public static byte[] decrypt(byte[] ciphertext) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decryptedOutput = cipher.doFinal(ciphertext);
		return decryptedOutput;
	}
	
	public static void main(String[] args) {
		// specify we want a key of 256 bits
		int keyBitSize = 256;
		byte[] plainText;
		
        Security.addProvider(new BouncyCastleProvider());
	    try {	
			generateKey(keyBitSize);
			//System.out.println("key : "+key.getEncoded());
			// Set the input		
			plainText = Files.readAllBytes(Path.of("test.txt"));
			//System.out.println("input: " + new String(plainText));
		    byte[] encryptedOutput = encrypt(plainText);
		    //System.out.println("Ciphertext : "+ new String(encryptedOutput));
		    //decrypt
		    byte[] decryptedOutput = decrypt(encryptedOutput);
		 	System.out.println("decoded input  : "+ new String(decryptedOutput));
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
		}
	} 

}
