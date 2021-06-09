package custom.libs;
import java.net.*;
import java.io.*;
  

public class FilesTransfer {
	
	public static void sendFile(String FILE_NAME,Socket socket) throws IOException {
    	
		FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        
        File myFile = new File (FILE_NAME);
        byte [] mybytearray  = new byte [(int)myFile.length()];
        fis = new FileInputStream(myFile);
        bis = new BufferedInputStream(fis);
        bis.read(mybytearray,0,mybytearray.length);
        os = socket.getOutputStream();
        System.out.println("Sending " + FILE_NAME + "(" + mybytearray.length + " bytes)");
        os.write(mybytearray,0,mybytearray.length);
        os.flush();
        System.out.println("Done.");
        
        if (bis != null) bis.close();
        if (os != null) os.close();
        
    }
	
	public static void saveFile(Socket socket, String FILE_TO_RECEIVED, int FILE_SIZE) throws IOException {
		
		int bytesRead;
	    int current = 0;
	    FileOutputStream fos = null;
	    BufferedOutputStream bos = null;
	    
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
    }

}
