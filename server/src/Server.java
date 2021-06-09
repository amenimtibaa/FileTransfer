import java.net.*;

import custom.libs.FilesTransfer;

import java.io.*;
  
public class Server
{
    //initialize socket and input stream
	public final static int FILE_SIZE = 6022386;
	public final static String FILE_TO_RECEIVED = "test.txt"; //"2.jpg"; //"graduate.docx";
	public final static String FILE_TO_SEND = "test.txt";
	private ServerSocket server;
    private Socket socket;
    private BufferedReader in;
  
    /**
     * @param port
     * */
    public Server(int port)
    {
        
        try
        {	// starts server and waits for a connection
            server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
  
            socket = server.accept();
            System.out.println("Client accepted"); 
            
            in = new BufferedReader(new InputStreamReader(
            		socket.getInputStream()));
            String clientSelection = in.readLine();
            while(true) 
            {        
	            switch (clientSelection) {
	            	case "1":
	            		FilesTransfer.saveFile(socket, FILE_TO_RECEIVED,  FILE_SIZE);
	            		System.out.println("Done saving !");
	            		continue;
	            	case "2":
	            		FilesTransfer.sendFile(FILE_TO_SEND, socket);
	            		System.out.println("File sent with success !");
	            		continue;
	            	case "3":
	            		System.out.println("Closing connection");
	            		socket.close();
	            		break;
                    default:
                        System.out.println("Incorrect command received.");
                        break;
	            }
            }
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
        
    }   
    	
    public static void main(String args[])
    {
        Server server = new Server(5000);    
    }
}