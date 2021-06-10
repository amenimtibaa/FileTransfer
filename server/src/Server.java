import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

  
public class Server
{

	private ServerSocket server;
	private ExecutorService pool=null;
    public static final int poolsize= 10;

  
    /**
     * @param port
     * */
    public Server(int port)
    {
        
        try
        {	// starts server and waits for a connection
            server = new ServerSocket(port);
            System.out.println("Server started");
            pool = Executors.newFixedThreadPool(poolsize);
            System.out.println("Waiting for a client ...");
            
            while(true) {
            	pool.execute(new Esclave(server.accept()));
            	// If you don't want to execute multiple clients : 
            	//new Thread(new Esclave(server.accept())).start();
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