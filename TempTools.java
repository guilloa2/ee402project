



import java.io.*;			
import java.util.Date;

//class about temperature sample
public class TempTools implements Serializable 
{
	static final long serialVersionUID = 1L;
	private static int temp_sample_number=0;
	private float temp;
	private Date dateTime;
	
	
	
	public TempTools(float Temp) //constructor
	{
		temp_sample_number++; //associate a temp and a sample number
		this.temp = Temp; 
		
	}

	public float Temp() //return the temperature
	{
		return this.temp;
	}
	
	
	public void set_Temp(float temp) //set the temperature
	{
		this.temp = temp;
	}
	
	public Date timeDate() //return the date and time
	{
		return this.dateTime;
	}
	
	
	public void set_time_Date(Date date) //set the date and time
	{
		this.dateTime = date;
	}
	
		
	}
