import java.net.*;
import java.io.*;
import java.io.IOException;

public class SocketConnect {
	
	private Socket socket;

	private InputStreamReader socketISR;
	private BufferedReader socketInput;
	
	private PrintWriter socketOutput;
	
	// NOTE: Main function should eventually be called from an external class
	public static void main(String[] args)
	{
		//Constructor called for SocketConnect Class, using John's Server
		//SocketConnect stockMarketConnection = new SocketConnect("194.80.49.34", 5000);
		
		//Constructor called for SocketConnect Class, using a Server launched locally
		SocketConnect stockMarketConnection = new SocketConnect("localhost", 5000);
	}
	
	// SocketConnect class holds a Java Socket which connect to an External Server.
	// This Class also contains methods for sending and recieving messages on the Socket.
	public SocketConnect(String host, int port)
	{
		try
		{			
            socket = new Socket(host, 5000); //Constructor for a Java Socket, using parameters passed to the constructor
            
            //ISR and BR objects are used to RECIEVE a message from the server
            socketISR = new InputStreamReader(socket.getInputStream());
            socketInput = new BufferedReader(socketISR);
            
            //PW objects are used to SEND a message to the server
            socketOutput = new PrintWriter(socket.getOutputStream());
            
            //Send the message 'HELO' to the server.
            SocketSendBytes("HELO");
            socket.close();
            
        }
        catch (IOException e){
            e.printStackTrace(System.err);
            return;
        }      		
    }
	
	public void SocketSendBytes(String thisMessage)
	{
		try
		{
			//Send message to server
			socketOutput.println(thisMessage);
			System.out.println("Message sent to the server : "+thisMessage);
			socketOutput.flush(); // NOTE: flush() is required to let the server know we are down sending a message
			
			//Store the message the server responds with
			String recieved = socketInput.readLine();		
			//Print the message out to the console
			System.out.println("Message recieved from server : " +recieved);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				//Ensure to close all Streams, otherwise calling close will crash the server
				socketOutput.close();
				socketISR.close();
				socketInput.close();
				socket.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}