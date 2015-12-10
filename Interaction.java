

//Class used to set and return request between client and server

import java.io.*;



public class Interaction implements Serializable{ 
	
	private static final long serialVersionUID = 1L;
	private String user_ask;
	private TempTools asample;
	
	
	//get and return the request of the user
	public String receive_request() 
	{
		return this.user_ask;
	}
	
	//get and return the sample
	public TempTools receive_sample() 
	{
		return this.asample;
	}

	
	public void setrequest(String request) //set the request
	{
		this.user_ask = request;
	}
	
	public Interaction() //constructor
	{
		this.asample = new TempTools(0.0f);
		
	}
	
	public Interaction(String request) 
	{
		this();
		this.user_ask = request;
	}
	
	
}
