


import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.*;
import java.awt.event.*;

import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
  The ClientGUI class allow to display an interface helping the client to communicate with the server
 The GUI is built using Swing and AWT
 */
public class ClientGUI extends JFrame implements MouseListener, ChangeListener
{
	
	
	
	private static final long serialVersionUID = 1L; // default serial ID version
	private Client client_app;
	private GraphTemp Bar_graph;
	private JTabbedPane Window;
	private JPanel display_temperature, temp_graph, graphParameter, Console, Parameters;
	private JButton refresh_graph; 
	private JLabel temp_label,temperature,title_graph;
	private JFormattedTextField  delay;
	private String server_IP;
	
	
	public ClientGUI() 
	{
		super("Temperature Application");		
		
		do {
			Action pop_up = new Action(this, "Enter Informations about the server", this, true);
			this.client_app = new Client(this.server_IP,this);
		} while (!client_app.Client_Connected());
		
		displayGUI();
					
		this.setResizable(false);
		this.setSize(850,450);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
    	this.client_app.set_Client_on_loop(new TempRequest(60000, this.client_app));
	}
	
	/*
	  Method to initialize the GUI components
	*/
	private void displayGUI()
	{
		//Initialize a window for the app
		display_temperature = new JPanel(new GridLayout(2,1));
			Console = new JPanel(new GridLayout(1,1));
			Console.setBorder(new TitledBorder("Console"));
			Console.setBackground(Color.white);
			temperature = new JLabel("Unknown");
			
			try {
			
				Font text = new Font("TimesRoman", Font.PLAIN,30);
				temperature.setFont(text);
				Font text1 = text.deriveFont(45f);
				temperature.setFont(text1);
			} catch (Exception e) { 
				System.out.println("Font: " + e);
			}
			temperature.setForeground(Color.green);
			temperature.setHorizontalAlignment(SwingConstants.CENTER);
			
	
			temp_label = new JLabel("Temperature detected");
			temp_label.setHorizontalAlignment(SwingConstants.CENTER);
		
					
					
		//initialize the temperature graph
		temp_graph = new JPanel(new BorderLayout());
		graphParameter = new JPanel(new FlowLayout());
					
			title_graph = new JLabel("Historical readings of the temperature", SwingConstants.LEFT);
			refresh_graph = new JButton("Refresh the graph");
			refresh_graph.addChangeListener(this);
		
			
		Window = new JTabbedPane();
		Window.addChangeListener(this);
		
		//display the settings window
				display_temperature.add(Console);
				
				
					Console.add(temp_label);
					
					Console.add(temperature);
					
				

			//display the graph
			graphParameter.add(refresh_graph);
			temp_graph.add(title_graph, BorderLayout.NORTH);
			temp_graph.add(graphParameter, BorderLayout.SOUTH);
			
			//TAB
			Window.addTab("Settings", display_temperature);
			Window.addTab("Graph", temp_graph);
			this.getContentPane().add("Center", Window);
			
	}
	
	public void display_temperature(String temperature_value, Interaction com) throws NullPointerException
	{
			
		this.temperature.setText(""+String.format("%.2f", com.receive_sample().Temp())+"°C");
			
	}
	
	  //Make the event when the client want to refresh the graph
	
	public void stateChanged(ChangeEvent action_user) {
		
		
	
		JTabbedPane window1 = (JTabbedPane) action_user.getSource();
			if(window1.getSelectedComponent() == temp_graph)
			{
				if(client_app.Sizerecordedtable()!=0)
				{
					refresh_graph.setEnabled(true);
					Bar_graph = new GraphTemp(client_app.recordedtable()); 
					temp_graph.add(Bar_graph);
					
				} else {
					refresh_graph.setEnabled(false);
					display_Message("Nothing has changed because no new temperature was recorded");
				}
			}
			if (action_user.getSource() == this.refresh_graph)
			{
				 Bar_graph.repaint();
			}
			
			
		}
	
	
	
	
	
	public Client New_Client()
	{
		return this.client_app;
	}
	
	
	public void set_Server_IP(String IP_server)
	{
		this.server_IP = IP_server;
	}
	
	
	
	 //launch the app 
		public static void main(String args[])
		{
			ClientGUI User = new ClientGUI();
		}
		
	//Implement mouse actions
	
    public void mousePressed(MouseEvent action_user) {
    	
    }
    
    public void mouseClicked(MouseEvent action_user) {
        
    	if(action_user.getSource() == this.delay)
        	this.delay.setText("");
     }

    public void mouseEntered(MouseEvent action_user) {
    	
    }
    
    public void mouseReleased(MouseEvent action_user) {
    	
    }

    public void mouseExited(MouseEvent action_user) {
    	
    }
 
    public void display_Message(String sentence)
	{
		JOptionPane.showMessageDialog(this, sentence);
	}
   
	
}


//pop-up to connect to the server
class Action extends JDialog implements ActionListener, MouseListener
{
		
	
	private static final long serialVersionUID = 1L;
		private JButton Connection;
        private ClientGUI Application;
        private JTextField IP_connect;
        private JTextField port_connect;
        

        public Action(Frame frame, String title, ClientGUI Application, boolean isModal) {
           super(frame, title, isModal);
           this.Application = Application;
           
           
           this.getContentPane().setLayout(new BorderLayout());

           JPanel Box = new JPanel(new BorderLayout(0,0));               
           JPanel Box1 = new JPanel(new GridLayout(3,4,20,20)); 
           Box1.setBackground(Color.white);
                      
           JLabel Port_display = new JLabel("Port is fixed to : "); 
           Port_display.setForeground(Color.red);
           port_connect = new JTextField("5050",50);
           port_connect.setBackground(Color.white);
           port_connect.addActionListener(this);
           port_connect.setEnabled(false);
           Box1.add(Port_display);
           Box1.add(port_connect);
           
	          JLabel IP_display = new JLabel("Enter the IP adress : ");
	           IP_connect = new JTextField("Write IP");
	           IP_connect.setColumns(10);
	           IP_connect.setBackground(Color.LIGHT_GRAY);
	           IP_connect.addActionListener(this);
	           IP_connect.addMouseListener(this);
	           Box1.add(IP_display);
	           Box1.add(IP_connect);
	           
	           JPanel bottom = new JPanel(new FlowLayout());
	           bottom.setBackground(Color.DARK_GRAY);
	
	           Connection = new JButton("let's get started");
	           Connection.addActionListener(this);
	           bottom.add(Connection);
           
           Box.add(Box1,BorderLayout.NORTH);
           Box.add(bottom,BorderLayout.SOUTH);
           this.getContentPane().add(Box);
           
           this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
           this.setResizable(true);
           this.setSize(500,200);
           this.setLocationRelativeTo(null); //open the window in the center of the screen
           this.setVisible(true);
        }
       public void actionPerformed(ActionEvent e)
        {	
        	
        			Application.set_Server_IP(this.IP_connect.getText());
        			this.dispose();
        	
        }
        
        //implement mouse actions
        public void mousePressed(MouseEvent action_user) {
        	
        }
        
        public void mouseClicked(MouseEvent action_user) {
            if(action_user.getSource() == this.IP_connect)
            	this.IP_connect.setText("");
         }

       

        public void mouseEntered(MouseEvent action_user) {
        	
        }

        public void mouseExited(MouseEvent action_user) {
        	
        }
        
 public void mouseReleased(MouseEvent action_user) {
        	
        }

        
}


