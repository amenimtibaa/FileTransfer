package custom.libs;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.crypto.*;
import java.security.*;
import java.security.cert.CertificateException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class bouncycastle {
	
	
	/** Generate a symetric encryption key and store it in a keystore file
	 * @param keyBitSize the size in bits  of the key
	 * @param KeyStorepassword the user's password to acces the keystore
	 * @param KeyStoreFilepath the path to the created keystore 
	 * @param Verbose a boolean to precise if we print execution trace on terminal or not
	 * */	
	public static void generateKey(int keyBitSize, String KeyStorepassword, String KeyStoreFilepath, boolean Verbose) throws GeneralSecurityException, IOException 
	{
		
		Security.addProvider(new BouncyCastleProvider());
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = new SecureRandom();
		generator.init(keyBitSize, secureRandom);
		SecretKey key = generator.generateKey();
		StoreToKeyStore(key, KeyStorepassword, KeyStoreFilepath);
		if(Verbose == true) 
		{
			System.out.println("Key generation done successfully"
					+ "\n"
					+ "Key stored in file : " + KeyStoreFilepath +" with success");
			
		}
	}
	
	/** Store secret key into a keystore located on the disk
	 * @param KeyToStore the secret key to store
	 * @param KeyStorepassword the password used to access the jks file
	 * @param KeyStoreFilepath of the jks file
	 * */
	private static void StoreToKeyStore(SecretKey KeyToStore, String KeyStorepassword, String KeyStoreFilepath) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException
	{
		File file = new File(KeyStoreFilepath);
		KeyStore javaKeyStore = KeyStore.getInstance("JCEKS");
		if(!file.exists()) 
		{
			javaKeyStore.load(null, null);
			javaKeyStore.setKeyEntry("keyAlias", KeyToStore, KeyStorepassword.toCharArray(), null);
			OutputStream writeStream = new FileOutputStream(KeyStoreFilepath);
			javaKeyStore.store(writeStream, KeyStorepassword.toCharArray());
		}
	}
	
	/** Return the secret key from the keystore located on disk
	 * @param filepath filepath to the keystore database
	 * @param password used to acces the keystore and a key entry
	 * */
	private static SecretKey LoadFromKeyStore(String filepath, String password) 
	{
		try 
		{
			KeyStore keyStore = KeyStore.getInstance("JCEKS");
			InputStream readStream = new FileInputStream(filepath);
			keyStore.load(readStream, password.toCharArray());
			SecretKey key = (SecretKey) keyStore.getKey("keyAlias", password.toCharArray());
			return key;
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
		
		return null;
		
	}
	
	/** encrypt an input file with key from the keystore, create a file with the result
	 * @param inputFilepath file to encrypt
	 * @param outputFilepath result file 
	 * @param KeyStoreFilepath the keystore filepath
	 * @param KeyStorepassword  the keystore password
	 * */
	public static void encryptFile(String inputFilepath, String outputFilepath, String KeyStoreFilepath, String KeyStorepassword) throws GeneralSecurityException, IOException {
		
		SecretKey key = LoadFromKeyStore(KeyStoreFilepath, KeyStorepassword);
		byte[] plainText = Files.readAllBytes(Path.of(inputFilepath));
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encryptedOutput = cipher.doFinal(plainText);
		// Write the encrypted result in a new file
		writeByte(encryptedOutput, outputFilepath);	
        
       	}
	
	/** decrypt an input file with key from the keystore, create a file with the result
	 * @param inputFilepath file to decrypt
	 * @param outputFilepath result file 
	 * @param KeyStoreFilepath the keystore filepath
	 * @param KeyStorepassword  the keystore password
	 * */
	public static void decryptFile(String inputFilepath, String outputFilepath, String KeyStoreFilepath, String KeyStorepassword) throws GeneralSecurityException, IOException {
		SecretKey key = LoadFromKeyStore(KeyStoreFilepath, KeyStorepassword);
		byte[] ciphertext = Files.readAllBytes(Path.of(inputFilepath));
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decryptedOutput = cipher.doFinal(ciphertext);
		writeByte(decryptedOutput, outputFilepath);	
	}
	
	/** write an array of bytes into a file
	 * @param bytes 
	 * @param outputFilepath result file 
	 * */
    private static void writeByte(byte[] bytes, String outputFilepath)
    {
        try 
        {
        	File file = new File(outputFilepath);
            OutputStream os = new FileOutputStream(file);
            os.write(bytes);
            os.close();
        }
  
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
	/** Test 
	 // Uncomment this to test
	public static void main(String[] args) {

		int keyBitSize = 256;
		String KeyStorepassword = "458391479854";
		String KeyStoreFilepath = "keystore.keystore";
		boolean Verbose = true; 
		String plainTextFilepath = "test.txt";
		String encryptedTextFilepath = "testEncrypted.txt";
		
	    try 
	    {	
	    	generateKey(keyBitSize, KeyStorepassword,  KeyStoreFilepath, Verbose);
			
	    	encryptFile(plainTextFilepath, encryptedTextFilepath, KeyStoreFilepath, KeyStorepassword);
	    	
	    	//decryptFile( encryptedTextFilepath, plainTextFilepath , KeyStoreFilepath, KeyStorepassword);
	    	
		} 
	    catch (GeneralSecurityException | IOException e) 
	    {
			e.printStackTrace();
		}	
	} 
	**/

}
