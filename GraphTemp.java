




import java.awt.*;
import javax.swing.*;
import java.util.*;

//draw the graph 
public class GraphTemp extends JPanel 
{
	static final long serialVersionUID = 1L;

	private ArrayList<TempTools> table;
	private int space = 22;
	private int Bottom_top = 25;
	private int gap = 8;
	

	/**
	 * Constructor
	 */
	public GraphTemp(ArrayList<TempTools> Table)
	{
		this.table = Table;
	}

	//paint the graph 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);


		int  x,w,h;
		int i=0;
		int j=0;
		float maximum = Value(0);
		//Max Value
		for(i=1; i<table.size(); i++)
			if(Value(i)>maximum)
				maximum = Value(i);

		w = ((getWidth()- (2*gap)) / table.size()) - space;
		x = gap;

		for(j=0; j<table.size(); j++)
		{
			h = (int)((getHeight()-Bottom_top) * ((double)Math.abs(Value(j)) / maximum));

			// rectangle
			
				g.setColor(Color.green);
		
			g.fill3DRect(x, getHeight() - h, w, h - Bottom_top, true);

			//Strings
			g.setColor(Color.black);
			g.drawString( String.format("%.2f", Value(j))+" °C", x+(w/2)-20, (getHeight()-h) - Bottom_top/2);

			x += (w + space);
		}
		//Draw the date of the last value
		g.setFont(new Font("Times roman", Font.PLAIN, 10));
		g.drawString("The last measure : " + getdatetime(table.size()-1), getWidth()/2 - 150, getHeight() - Bottom_top/4);

	}
	
	private String getdatetime(int tamp)
	{
		return (""+table.get(tamp).timeDate());
	}
	
	
	private float Value(int tamp)
	{
		return (table.get(tamp).Temp());
	}
	
	
	
}
