import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.security.GeneralSecurityException;

public class testClientSever 
{
	public static final int SERVER_PORT = 5000; 
	public static final String SERVER = "localhost";
	public static final boolean VERBOSE = true; 
	public static final int keyBitSize = 256;
	
	/**Print and return the number of action to take
	 * */
	public static String selectAction() throws IOException 
	{
        System.out.println("1. Send file.");
        System.out.println("2. Recieve file.");
        System.out.println("3. Exit.");
        System.out.print(" Type the number of the Action you want to do : ");
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        return stdin.readLine();
    }
	
	public static void main(String args[]) throws FileNotFoundException
    {	
    	String KeyStorepassword = "458391479854";  
    	String KeyStoreFilepath = "keystore.keystore";
    	
    	String plainTextFilepath = "test.txt";
    	String encryptedTextFilepath = "testEncrypted.txt";
    	int FILE_SIZE = 6022386;
    	PrintStream os = null;
    	
    	//Establish connection with server and load key encryption into a keystore
    	clientFile client = new clientFile(SERVER, SERVER_PORT, KeyStoreFilepath, KeyStorepassword);  
    	try 
    	{
			os = new PrintStream(client.clientSocket.getOutputStream());
	    	
    		switch (Integer.parseInt(selectAction())) 
    		{
    		case 1:
    			os.println("1");
                client.sendFile(plainTextFilepath, encryptedTextFilepath);
                break;
            case 2:
            	os.println("2");
            	//BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
                //System.out.print("Enter file name: ");
                //fileName = stdin.readLine();
                client.receiveFile(plainTextFilepath, plainTextFilepath, FILE_SIZE);
                break;
            case 3:
            	os.println("3");
            	client.clientSocket.close();
   			 	break;
            default:
                System.out.println("Incorrect command received.");
                break;
    		}
    		if(client.clientSocket != null) client.clientSocket.close();
	    	
    	}
    	catch (GeneralSecurityException | IOException e) 
    	{
			e.printStackTrace();
		}
    	finally 
    	{
    		
    	}
    }

}
	
