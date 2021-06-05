// A Java program for a Server
import java.net.*;
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
            saveFile(socket);
            System.out.println("Done saving ! ");
            
            System.out.println("Closing connection");
            
            // close connection
            socket.close();

        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
    
    private void saveFile(Socket clientSock) throws IOException {
		
		int bytesRead;
	    int current = 0;
	    FileOutputStream fos = null;
	    BufferedOutputStream bos = null;
		// receive file
	    byte [] mybytearray  = new byte [FILE_SIZE];
	    InputStream is = socket.getInputStream();
	    fos = new FileOutputStream(FILE_TO_RECEIVED);
	    bos = new BufferedOutputStream(fos);
	    bytesRead = is.read(mybytearray,0,mybytearray.length);
	    current = bytesRead;
	
	    do {
	       bytesRead = is.read(mybytearray, current, (mybytearray.length-current));
	       if(bytesRead >= 0) current += bytesRead;
	    } while(bytesRead > -1);
	
	    bos.write(mybytearray, 0 , current);
	    bos.flush();
	    System.out.println("File " + FILE_TO_RECEIVED
	        + " downloaded (" + current + " bytes read)");
	    
	    if (fos != null) fos.close();
	    if (bos != null) bos.close();
	    if (socket != null) socket.close();
    }
    
    	
    public static void main(String args[])
    {
        Server server = new Server(5000);
        
            }
}