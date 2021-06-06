import java.net.*;

import files.handler.FilesTransfer;

import java.io.*;

public class clientServer{
	
	public final static String FILE_NAME = "2.jpg";  // "graduate.docx"; 
	public final static int SOCKET_PORT = 5000; 
	public final static String SERVER = "localhost";
    
	private Socket socket			 = null;
  
    public clientServer(String address, int port)
    {
        // establish a connection
        try
        {
	        socket = new Socket(address, port);
            System.out.println("Connected");
            //Send a file
            FilesTransfer.sendFile(FILE_NAME, socket);
            System.out.println("done sending !");
            if (socket!=null) socket.close();
            
        }
        catch(IOException i) 
        {
        	System.out.println(i);
        }
        catch(Exception u)
        {
            System.out.println(u);
        }
        
    }
    
    public static void main(String args[]) throws FileNotFoundException
    {	
    	clientServer client = new clientServer(SERVER, SOCKET_PORT);  
    	
         
    }
    
}