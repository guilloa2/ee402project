
/* The Connection Handler Class - Written by Derek Molloy for the EE402 Module
 * See: ee402.eeng.dcu.ie
 */


import java.net.*;
import java.io.*;


public class ThreadedConnectionHandler extends Thread
{
    private Socket clientSocket = null;				// Client socket object
    private ObjectInputStream is = null;			// Input stream
    private ObjectOutputStream os = null;			// Output stream
    private Interaction interact;
    private DateTimeService theDateService;
    private Tmp36 theTemperatureService;
      
    //The constructor for the connection handler 
    public ThreadedConnectionHandler(Socket clientSocket,DateTimeService theDateService,Tmp36 theTemperatureService) {
    	 
    	this.clientSocket = clientSocket;
        this.theDateService = theDateService;
        this.theTemperatureService = theTemperatureService;     
    }

    public void run() 
    {
         try {
            this.is = new ObjectInputStream(clientSocket.getInputStream());
            this.os = new ObjectOutputStream(clientSocket.getOutputStream());
            while (this.readCommand()) {}
         } 
         catch (IOException e) 
         {
        	System.out.println("XX. There was a problem with " + "the Input/Output Communication:");
            e.printStackTrace();
         }
         
    }

    // Receive and process incoming string commands from client socket 
    private boolean readCommand() 
    {
    	try {
            this.interact = (Interaction)is.readObject();
        } 
        catch (Exception e) {    
        	this.closeSocket();
            return false;
        }
    	
        System.out.println("01. <- Received a String object from"
        					+ " the client (" + interact + ").");

        switch (this.interact.receive_request())
        {
        	case "Temperature_read": this.Temperature_read();
        					send(this.interact);
        					break;
        	
        	default: this.sendError("Error command: " + interact);
	        		break;
        }
      
        return true;
    }
    
    
    //Read the temperature and the date/time
    private void Temperature_read() {
    	this.interact.receive_sample().set_Temp(theTemperatureService.Temperature_read());
    	this.interact.receive_sample().set_time_Date(this.theDateService.getDateAndTime());
    	
    	
    }
    
   
     // Send a generic object back to the client
     private void send(Object o) {
        try {
            System.out.println("02. -> Sending (" + o +") to the client.");
            this.os.writeObject(o);
            this.os.flush();
        } 
        catch (Exception e) {
            System.out.println("XX." + e.getStackTrace());
        }
    }

   
     // Send a pre-formatted error message to the client 
    public void sendError(String message) { 
        this.send("Error:" + message);	
    }
    
   
     // Close the client socket 
    public void closeSocket() { 
        try {
            this.os.close();
            this.is.close();
            this.clientSocket.close();
        } 
        catch (Exception e) {
            System.out.println("XX. " + e.getStackTrace());
        }
    }
    
}
