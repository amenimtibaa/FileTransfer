import java.net.*;
import java.security.GeneralSecurityException;

import custom.libs.FilesTransfer;
import custom.libs.bouncycastle;

import java.io.*;

public class clientFile{
	
	public static boolean VERBOSE = true; 
	public static int keyBitSize = 256;
	public int SERVER_PORT = 5000; 
	public String SERVER = "localhost";
	public Socket clientSocket = null;
	private String KeyStoreFilepath;
	private String KeyStorepassword;
  
	/** Establish a connection with the server and generate encryption key stored locally
	 * @param KeyStoreFilepath 
	 * 
	 * */
	public clientFile(String serverAddress, int serverPort, String KeyStoreFilepath,String KeyStorepassword)
    {
        try
        {	this.SERVER = serverAddress;
        	this.SERVER_PORT = serverPort;
        	this.clientSocket = new Socket(SERVER, SERVER_PORT);
	        if (VERBOSE) {System.out.println("----Connected To Sever----");}
            bouncycastle.generateKey(keyBitSize, KeyStorepassword,  KeyStoreFilepath, VERBOSE);
            this.KeyStoreFilepath = KeyStoreFilepath;
            this.KeyStorepassword = KeyStorepassword;
        }
        catch(IOException e) 
        {
        	e.printStackTrace();
        } catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * @param plainTextFilepath the file to encrypt
	 * @param encryptedTextFilepath the encrypted file to send
	 * @throws IOException 
	 * @throws GeneralSecurityException 
	 * 
	 * */
	public void sendFile(String plainTextFilepath, String encryptedTextFilepath) throws GeneralSecurityException, IOException 
	{
		bouncycastle.encryptFile(plainTextFilepath, encryptedTextFilepath, KeyStoreFilepath, KeyStorepassword);
        //Send the encrypted file
        FilesTransfer.sendFile(encryptedTextFilepath, clientSocket);
        if (VERBOSE) {System.out.println("File" + encryptedTextFilepath + "successfully sent");}
	}
    
	/**
	 * @throws IOException 
	 * @throws GeneralSecurityException 
	 * */
	public void receiveFile(String encryptedTextFilepath, String plainTextFilepath, int FILE_SIZE) throws IOException, GeneralSecurityException 
	{
		FilesTransfer.saveFile(clientSocket, encryptedTextFilepath,  FILE_SIZE);
		if (VERBOSE) {System.out.println("Encrypted File : " + encryptedTextFilepath + " successfully received");}
		bouncycastle.decryptFile( encryptedTextFilepath, plainTextFilepath , KeyStoreFilepath, KeyStorepassword);
		if (VERBOSE) {System.out.println("File : " + plainTextFilepath + " successfully decrypted and saved");}
	}
	
    /*
	public static void main(String args[]) throws FileNotFoundException
    {	
    	String KeyStorepassword = "458391479854";
    	//Socket clientSocket;
    	int SERVER_PORT = 5000; 
    	String SERVER = "localhost";

    	String KeyStoreFilepath = "keystore.keystore";
    	
    	String plainTextFilepath = "test.txt";
    	String encryptedTextFilepath = "testEncrypted.txt";
    	int FILE_SIZE = 6022386;
    	
    	clientFile client = new clientFile(SERVER, SERVER_PORT, KeyStoreFilepath, KeyStorepassword);  
    	
    	try {
    		client.sendFile(plainTextFilepath, encryptedTextFilepath);
    		//client.receiveFile(plainTextFilepath, plainTextFilepath, FILE_SIZE);
			//if (clientSocket!=null) {clientSocket.close();}
	         
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
		}
    }
    */
    
}