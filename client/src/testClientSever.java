import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.security.GeneralSecurityException;

public class testClientSever {
	
	public static final int SERVER_PORT = 5000; 
	public static final String SERVER = "localhost";
	public static final boolean VERBOSE = true; 
	public static final int keyBitSize = 256;
	
	public static void main(String args[]) throws FileNotFoundException
    {	clientFile client;
    	String KeyStorepassword = "458391479854";
    	//Socket clientSocket;
    	
    	String KeyStoreFilepath = "keystore.keystore";
    	
    	String plainTextFilepath = "test.txt";
    	String encryptedTextFilepath = "testEncrypted.txt";
    	int FILE_SIZE = 6022386;
    	
    	client = new clientFile(SERVER, SERVER_PORT, KeyStoreFilepath, KeyStorepassword);  
    	
    	try {
    		client.sendFile(plainTextFilepath, encryptedTextFilepath);
    		//client.receiveFile(plainTextFilepath, plainTextFilepath, FILE_SIZE);
			//if (clientSocket!=null) {clientSocket.close();}
	         
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
		}
    }

}
