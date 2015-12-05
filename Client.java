/* The Client Class - Written by Derek Molloy for the EE402 Module
 * See: ee402.eeng.dcu.ie
 * 
 * 
 */

//package ee402;

import java.net.*;
import java.io.*;
import java.util.*;
/**
 * Client is a class in the context of Client/Server application that represents
 * the Client. This class manages the connection to the server, the sending and 
 * reception of the Data
 * The data which is sent is a Command Object.
 * 
 * @see Command
 */
public class Client {
	
	private static int portNumber = 5050;
    private Socket socket = null;
    private ObjectOutputStream os = null;
    private ObjectInputStream is = null;
    
    private ClientGUI cgui;
    private Command clientCommand;
    private ArrayList<Sample> historical;
    private boolean isConnected = false;
    private LoopClient lc =null;
    
    /**
	 * Constructor of class 
	 * 
	 * @param serverIP String which is the IP of the server (i.e XXX.XXX.X.X)
	 * @param cgui ClientGUI 
     */
    public Client(String serverIP, ClientGUI cgui) {
    	this(serverIP);
    	this.cgui = cgui;
    }
	
    /**
	 * Constructor of class 
	 * 
	 * @param serverIP String which is the IP of the server (i.e XXX.XXX.X.X)
	 */    
    public Client(String serverIP) {
    	this.cgui = cgui;
    	this.clientCommand = new Command();
    	this.historical = new ArrayList<Sample>();
    	if (!connectToServer(serverIP)) {
    		System.out.println("XX. Failed to open socket connection to: " + serverIP);            
    	} else this.isConnected = true;
    }
    
    /**
     * Method that creates the connection between Client and Server.
     * 
     * @param serverIP String which is the IP of the server (i.e XXX.XXX.X.X)
     * @return boolean to test if the connection was successfull
     */
    private boolean connectToServer(String serverIP) {
    	try {  
    		System.out.println("Please wait for connection...");
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
    
    /**
     * Method to send a query to server and receive response
     * Is a Command object is passed to the method it means 
     * the query wants to send data to the server
     * Otherwise the query request data from the server 
     *
     * @param str String representing the query (i.e "getTemp")
     * @return com Command with all information of the query
     */
    public synchronized Command query(String str, Command c) {
    	Command com;
    	if (c == null)
    		com = new Command(str);
    	else {
    		com = c;
    		com.setCommandLine(str);
    	}
    	
    	System.out.println("01. -> Sending Command (" + com + ") to the server...");
    	System.out.println("Sending: "+ com.getSample().getTriggers(0)+" "+com.getSample().getTriggers(1));
    	this.send(com);
    	try {
    		com = (Command)receive();
    		System.out.println("Receiving: "+ com.getSample().getTriggers(0)+" "+com.getSample().getTriggers(1));
    		System.out.println("05. <- The Server responded with: ");
    		System.out.println("    <- " + com);
    	} catch (Exception e) {
    		System.out.println("XX. There was an invalid object sent back from the server");
    	}	

    	return com;
    }
	
    /**
     * Override of the query function that takes only a String
     * For request informations querys (i.e. GetTemp, GetTriggers)
     */
    public Command query(String str) {
    	return query(str,null);
    }
    
    /**
     * Method to send generic objects
     * 
     * @param o	generic Object
     */
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

    /**
     * Method to receive generic objects
     * 
     * @return o generic Object	
     */
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
    
    /**
     * Getter of Command attribute
     * 
     * @return Command
     */
    public Command getClientCommand()
    {
    	return this.clientCommand;
    }
    
    public void setClientCommand(Command command)
    {
    	this.clientCommand = command;
    }
    
    /**
     * ClientGUI getter
     */
    public ClientGUI getClientGUI()
    {
    	return this.cgui;
    }
    
    /**
     * ArrayList getter that contains the 10 last sample
     */
    public ArrayList<Sample> getHistorical()
    {
    	return this.historical;
    }
    
    /**
     * ArrayList size getter
     */
    public int getSizeHistorical()
    {
    	return historical.size();
    }
    
    /**
     * Boolean isConnected getter to check if the connection went well
     */
    public boolean isConnected()
    {
    	return isConnected;
    }
    
    /**
     * LoopClient getter.
     * 
     * @see LoopClient
     */
    public LoopClient getLoopClient()
    {
    	return this.lc;
    }
    
    /**
     * LoopClient setter
     * 
     */
    public void setLoopClient(LoopClient lc)
    {
    	this.lc = lc;
    }
    
}
