// A Java program for a Server
import java.net.*;

import custom.libs.FilesTransfer;

import java.io.*;
  
public class Server
{
    //initialize socket and input stream
	public final static int FILE_SIZE = 6022386;
	public final static String FILE_TO_RECEIVED = "2.jpg"; //"graduate.docx";
	
	private ServerSocket server;
    private Socket socket;
    private DataInputStream in;
  
    public Server(int port)
    {
        
        try
        {	// starts server and waits for a connection
            server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
  
            socket = server.accept();
            System.out.println("Client accepted"); 
            FilesTransfer.saveFile(socket, FILE_TO_RECEIVED,  FILE_SIZE);
            System.out.println("Done saving !");
            
            System.out.println("Closing connection");
            
            // close connection
            socket.close();

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