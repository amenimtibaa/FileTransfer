import java.io.*;
import java.net.*;
import custom.libs.FilesTransfer;


public class Esclave implements Runnable{
	Socket socket ;
	
    public Esclave(Socket socket){
        this.socket = socket;
    }
    
    @Override
    public void run() {
    	BufferedReader in;
    	int FILE_SIZE = 6022386;
    	String FILE_NAME = "test.txt";
    	
    	System.out.println("Client accepted"); 
        try 
        {
        	in = new BufferedReader(new InputStreamReader(
            		socket.getInputStream()));
            String clientSelection = in.readLine();
                    
            switch (clientSelection) {
            	case "1":
            		FilesTransfer.saveFile(socket, FILE_NAME,  FILE_SIZE);
            		System.out.println("Done saving !");
            		break;
            	case "2":
            		FilesTransfer.sendFile(FILE_NAME, socket);
            		System.out.println("File sent with success !");
            		break;
            	case "3":
            		System.out.println("Closing connection");
            		socket.close();
            		break;
                default:
                    System.out.println("Incorrect command received.");
                    break;
            }
        	
        }
        catch(IOException i)
        {	
            System.out.println(i);
        }
        
    }
	
}
