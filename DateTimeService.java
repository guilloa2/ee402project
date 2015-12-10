

/* The Date Time Service Class - Written by Derek Molloy for the EE402 Module
 * See: ee402.eeng.dcu.ie
 */


import java.util.Calendar;
import java.util.Date;

//get the date for samples
public class DateTimeService
{
   //return date or time
   public Date getDateAndTime()
   {
	 Date date = Calendar.getInstance().getTime();
	 return date;	
   }	
}
