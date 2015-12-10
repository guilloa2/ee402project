

/* The Client Class - Written by Derek Molloy for the EE402 Module
 * See: ee402.eeng.dcu.ie
 * 
 * 
 */

//package ee402;

import java.net.*;
import java.io.*;
import java.util.*;

//Client class: connection to the server
public class Client {
	
	private static int portNumber = 5050;
    private Socket socket = null;
    private ObjectOutputStream os = null;
    private ObjectInputStream is = null;
    private Interaction Client_action;
    private ClientGUI User;
    private TempRequest client_loop =null;
    private ArrayList<TempTools> previous_temp_recorded;
    private boolean Connected = false;
    
    
  //Constructor 
    public Client(String serverIP, ClientGUI User) {
    	this(serverIP);
    	this.User = User;
    }
	
    //Constructor 
    public Client(String serverIP) {
    	this.User = User;
    	this.Client_action = new Interaction();
    	this.previous_temp_recorded = new ArrayList<TempTools>();
    	if (!connectToServer(serverIP)) {
    		System.out.println("XX. Failed to open socket connection to: " + serverIP);            
    	} else this.Connected = true;
    }
    
   
    private boolean connectToServer(String serverIP) {
    	try {  
    		System.out.println("We are trying to connect you to the server");
    		this.socket = new Socket(serverIP,portNumber);
    		this.os = new ObjectOutputStream(this.socket.getOutputStream());
    		this.is = new ObjectInputStream(this.socket.getInputStream());
    		System.out.println("00. -> Connected to Server:" + this.socket.getInetAddress() 
    				+ " on port: " + this.socket.getPort());
    		System.out.println("    -> from local address: " + this.socket.getLocalAddress() 
    				+ " and port: " + this.socket.getLocalPort());
    	} 
        catch (Exception e) {
        	System.out.println("XX. Failed to Connect to the Server at port: " + portNumber);
        	System.out.println("    Exception: " + e.toString());	
        	return false;
        }
		return true;
    }
    
  //Send a request to the server
   public synchronized Interaction Client_req(String sentence, Interaction req) {
    	Interaction ask;
    	if (req == null)
    		ask = new Interaction(sentence);
    	else {
    		ask = req;
    		ask.setrequest(sentence);
    	}
    	
    	System.out.println("01. -> Sending request (" + ask + ") to the server...");
    	
    	this.send(ask);
    	try {
    		ask = (Interaction)receive();
    		
    		System.out.println("05. <- The Server responded with: ");
    		System.out.println("    <- " + ask);
    	} catch (Exception e) {
    		System.out.println("XX. There was an invalid object sent back from the server");
    	}	

    	return ask;
    }
	
    //Client_req when there is only a string in the request
    public Interaction Client_req(String str) {
    	return Client_req(str,null);
    }
    
  
     //Method to send generic objects
       private void send(Object o) {
		try {
		    System.out.println("02. -> Sending an object...");
		    os.writeObject(o);
		    os.flush();
		} 
	    catch (Exception e) {
		    System.out.println("XX. Exception Occurred on Sending:" +  e.toString());
		}
    }

    
     // Method to receive generic objects
        private Object receive() 
    {
		Object o = null;
		try {
			System.out.println("03. -- About to receive an object...");
		    o = is.readObject();
		    System.out.println("04. <- Object received...");
		} 
	    catch (Exception e) {
		    System.out.println("XX. Exception Occurred on Receiving:" + e.toString());
		}
		return o;
    }
    
    
    
    public ClientGUI recover_ClientGUI() //recover the client
    {
    	return this.User;
    }
    
    //table of recorded temp
    public ArrayList<TempTools> recordedtable()
    {
    	return this.previous_temp_recorded;
    }
    
   
    public int Sizerecordedtable() //get the size of the temperature table
    {
    	return previous_temp_recorded.size();
    }
    
   
    
    public void set_Client_on_loop(TempRequest client_loop) //set the client in the loop
    {
    	this.client_loop = client_loop;
    }
    
    public TempRequest Client_loop() //get the client
    {
    	return this.client_loop;
    }
    
  //Client is connected
    public boolean Client_Connected()
    {
    	return Connected;
    }
    
    public Interaction Client_request()
    {
    	return this.Client_action;
    }
    
    public void set_Client_request(Interaction command)
    {
    	this.Client_action = command;
    }
   
    
}
