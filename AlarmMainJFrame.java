import javax.swing.JFrame;
import javax.swing.JPanel;

/*makinthan PRABHAHARAN
 * jUNE 11
 * */

//this is the main class of the program.  It is the JFrame that holds all of the pages of the Alarm clock together
public class AlarmMainJFrame extends JFrame {
	
	//declaring a string constant to hold the directory of all of the files used in the program
	final static String GAMEFILES = "C:\\Users\\padch\\OneDrive\\Documents\\SummativeICS3UFiles" + "\\";
	
	//initializing a JPanel on which all of the pages of the alarm clock will be displayed
	static JPanel pageDisplayJPanel = new JPanel();
	
	//initializing a Jpanel object that will hold the current Jpanel that is occupying the pageDisplayJPanel
	static JPanel currentJPanel = new JPanel();
	
	//initializing the home screen page for the program
	static AlarmHomeScreen alarmHomeScreen = new AlarmHomeScreen();
	
	//initializing the screen that will display when the alarm is triggered
	static AlarmRingingScreen alarmRingingScreen = new AlarmRingingScreen();
	
	//initializing the screen that is used to add or delete alarms
	static AlarmManagerScreen alarmManagerScreen = new AlarmManagerScreen();
	
	//creating a null TimeKeeper object
	TimeKeeper timeKeeper = null;
	
	
	//this is the constructor for the main JFrame
	public AlarmMainJFrame() {
		
		//initializing the TimeKeeper object which loops in the background to check if it is time for any of the alarms to ring
		timeKeeper = new TimeKeeper();
		
		//setting the size, layout and default close operation of the JFrame
		//the close operation is set to do nothing so that the user cannot dismiss the alarm by simply exiting the program
		setSize(600,600);
		setLayout(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		
		//setting the location, size and layout of the page display Jpanel and adding it to the main JFrame
		pageDisplayJPanel.setBounds(0, 0, 600, 600);
		pageDisplayJPanel.setLayout(null);
		add(pageDisplayJPanel);
		
		//setting the visibility of the JFrame to true
		setVisible(true);
		
	}
	
	//this static method is used to display any of the pages of the Alarm clock.  It takes the panel to display as a parameter
	public static void displayJPanel(JPanel panelToDisplay) {
		
		//checking if there are any components on the page Display panel in the first place
		if(pageDisplayJPanel.getComponents().length > 0) {
			//if there are components on the pageDisplayPanel
			
			//setting the visibility of the current jpanel being displayed to false
			currentJPanel.setVisible(false);
			
			//removing the current jpanel from the pageDisplayPanel
			pageDisplayJPanel.remove(currentJPanel);
		}
		
		//setting the location and visibility of the panel to be displayed, and adding it to the pageDisplayPanel
		panelToDisplay.setLocation(0, 0);
		panelToDisplay.setVisible(true);
		pageDisplayJPanel.add(panelToDisplay);
		
		//setting the panel to display to the currentJpanel variable
		currentJPanel = panelToDisplay;
		
	}

	//this is the main method of the program
	public static void main(String[] args) {

		//initializing the alarmMainJFrame object, to actually display the Jframe on the screen
		//this instance of the object will not be used later, however.
		AlarmMainJFrame alarmMainJFrame = new AlarmMainJFrame();
		
		//displaying the home screen on the mainJframe
		AlarmMainJFrame.displayJPanel(AlarmMainJFrame.alarmHomeScreen);
		

	}

}
