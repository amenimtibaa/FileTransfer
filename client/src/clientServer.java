import java.net.*;
import java.io.*;


	  
public class clientServer{
	
    // initialize socket and input output streams
	public final static String FILE_TO_SEND = "2.jpg";  // "graduate.docx"; 
    private Socket socket			 = null;
    private DataInputStream  input   = null;
    private DataOutputStream out     = null;
  
    // constructor to put ip address and port
    public clientServer(String address, int port)
    {
        // establish a connection
        try
        {
	            socket = new Socket(address, port);
            System.out.println("Connected");
            
            sendFile("1.jpeg");
            System.out.println("done sending !");
            
        }
        catch(IOException i) 
        {
        	System.out.println(i);
        }
        catch(Exception u)
        {
            System.out.println(u);
            System.out.println("Not Connected");
        }
        
    }
    
    public void sendFile(String file) throws IOException {
    	FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
		// send file
        File myFile = new File (FILE_TO_SEND);
        byte [] mybytearray  = new byte [(int)myFile.length()];
        fis = new FileInputStream(myFile);
        bis = new BufferedInputStream(fis);
        bis.read(mybytearray,0,mybytearray.length);
        os = socket.getOutputStream();
        System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
        os.write(mybytearray,0,mybytearray.length);
        os.flush();
        System.out.println("Done.");
        
        if (bis != null) bis.close();
        if (os != null) os.close();
        if (socket!=null) socket.close();
    }
    
    public static void main(String args[]) throws FileNotFoundException
    {	
    	clientServer client = new clientServer("localhost", 5000);  
    	    	
         
    }
    
}