

import java.io.*;

//read the temperature thanks to the temperature sensor connected on the BeagleBone
public class Tmp36{ 
	
	//Path of the file where we can read the temperature
	private static String LDR_PATH="/sys/bus/iio/devices/iio:device0/in_voltage";
	private BufferedReader buffer; 
			
	
	//read and get the temperature
	public float Temperature_read() {
		float temp=0.0f;
		int ain = 0;
		
		try {
			buffer = new BufferedReader( new FileReader(LDR_PATH + ain + "_raw"));
			String number;
			number = buffer.readLine();
			
			temp=Celcius(number);	
			System.out.println("The Temperature is :" + temp);
			
		}  catch (Exception e) {
			System.out.println("Error: " + e);
		}
		
		return temp;
	}
	
	//Convert the adc value into a Celsius value of temperature
	private  float Celcius(String number)
	{	
		int adc_Value;
		try {
			adc_Value = Integer.parseInt(number);
		} catch (Exception e) {
			return 0;
		}
		//method explains on the tmp36.cpp given by Derek Molloy
		float cur_voltage = adc_Value * (1.80f/4096.0f);
		float diff_degreesC = (cur_voltage-0.75f)/0.01f;
		return (25.0f + diff_degreesC);
	}
	
}
