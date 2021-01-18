
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
/*makinthan PRABHAHARAN
 * jUNE 11
 * */

//this is the screen of the Alarm clock that the user uses to add and delete alarms
//the alarms are saved to a text file, and the program reads from the text files at startup to display them in a JList
public class AlarmManagerScreen extends JPanel {
	
	//declaring two files that are used for the tone to be played for each alarm, and putting them inside an array of type file
	File toneFile1 = new File(AlarmMainJFrame.GAMEFILES + "bmwchime.wav");
	File toneFile2 = new File(AlarmMainJFrame.GAMEFILES + "cashsound.wav");
	File toneFilesArray[] = {toneFile1, toneFile2};
	
	//initializing the text file that the program will read and write from to store alarms in the computer's memory
	File alarmFile = new File("alarms.txt");
	
	//declaring a printWriter object to write to the alarms file
	PrintWriter alarmWriter = null;
	
	//Declaring a filereader object to read from the alarms file
	FileReader alarmFileReader = null;
	
	public AlarmManagerScreen() {
		
		
		//getting the raw data from the file.  It is raw because it has not yet been split up into its time and sound effect coutnerparts.
		String rawFileDataString = readRawFileData();
		
		//declaring a scanner to detect each line in the rawFileDataString, which is essentially a copy of the text in the text file
		Scanner rawDataScanner = new Scanner(rawFileDataString);
		
		//while there are additional lines in the text document
		while(rawDataScanner.hasNextLine()) {
			
			//reading each line and storing it inside a variable
			String alarmInfo = rawDataScanner.nextLine();
			
			/*splitting each line by the regex, "-".  The alarm is written into the file in the following
			 * format: "HH:mm:ss-toneid" where HH:mm:ss is the time for the alarm to ring and toneid is the integer
			 * index of the tone file to be played in terms of the array of tone files (toneFilesArray)*/
			String splitAlarmInfo[] = alarmInfo.split("-");
			
			//getting the time of the alarm by accessing the 0th inde of the splitAlarmInfo array
			String alarmTimeString = splitAlarmInfo[0];
			
			//getting the index of the tone file by parsing index 1 of the splitAlarmInfo array
			int toneIndex = Integer.parseInt(splitAlarmInfo[1]);
			
			//creating a new alarm object, with the time string read fromt he file and also the tone file that it must play
			Alarm addedAlarm = new Alarm(alarmTimeString, toneFilesArray[toneIndex]);
			
			//adding the alarm to the arraylist of alarms in the TimeKeeper class
			TimeKeeper.arrayListOfAlarms.add(addedAlarm);
			
			//adding the time of the alarm to the arraylist of allarm times in the timekeeper class
			TimeKeeper.arrayListOfAlarmTime.add(addedAlarm.strTimeToRing);
			
		}
		
		//setting the size, color and layout of the alarm manager screen
		setSize(600, 600);
		setBackground(new Color(0,150,255));
		setLayout(null);
		
		//using a JList inside of a JScrollPanel to display the times of the alarms that currently exist
		JScrollPane jScrollPane = new JScrollPane();
		JList<Object> jListOfAlarms = new JList<Object>();
		jListOfAlarms.setListData(TimeKeeper.arrayListOfAlarmTime.toArray());
		jScrollPane.setViewportView(jListOfAlarms);
		jScrollPane.setBounds(200, 20, 200, 170);
		add(jScrollPane);
		
		
		//text field to store the hour of the alarm to ring
		JTextField jTextFieldHour = new JTextField("HH");
		jTextFieldHour.setBounds(200, 195, 50, 20);
		
		//text field to store the minute of the alarm to ring
		JTextField jTextFieldMinute = new JTextField("mm");
		jTextFieldMinute.setBounds(250, 195, 50, 20);
		
		//text field to store the second of the alarm to ring
		JTextField jTextFieldSecond = new JTextField("ss");
		jTextFieldSecond.setBounds(300, 195, 50, 20);
		
		//Jcombobox used to store whether or not the alarm will ring AM or PM
		JComboBox<String> amOrPmComboBox = new JComboBox<String>(new String[] {"AM", "PM"});
		amOrPmComboBox.setBounds(350, 195, 50, 20);
		
		//adding the text fields and the combobox to the jpanel
		add(jTextFieldHour);
		add(jTextFieldMinute);
		add(jTextFieldSecond);
		add(amOrPmComboBox);
		
		//this combo box is used to select the tone of the alarm that the user wants to play when the alarm rings
		JComboBox<String> toneFileSelector = new JComboBox<String>(new String[] {"Alarm Tone 1", "Alarm Tone 2"});
		toneFileSelector.setBounds(200, 220, 200, 50);
		add(toneFileSelector);
		
		//button used to add alarms
		JButton btnAddAlarm = new JButton("Add Alarm");
		btnAddAlarm.setBounds(200, 280, 100, 75);
		btnAddAlarm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				//checking if there is text
				if(jTextFieldHour.getText().length() > 0 && jTextFieldMinute.getText().length() > 0 && jTextFieldSecond.getText().length() > 0) {
					
					try {
						
						//setting the alarm time string which wiill hold the time the alarm is to ring
						String alarmTimeString = jTextFieldHour.getText() + ":" + jTextFieldMinute.getText() + ":" + jTextFieldSecond.getText() + " " + amOrPmComboBox.getSelectedItem();
						
						//setting the tone file to the one selected from the cmobobox
						File toneFileToPlay = toneFilesArray[toneFileSelector.getSelectedIndex()];
						
						//creating a new alarm object t hold the newly created alarm
						Alarm createdAlarm = new Alarm(alarmTimeString, toneFileToPlay);
						
						//adding th enew alarm to the list of alrms in the TimeKeeper
						TimeKeeper.arrayListOfAlarms.add(createdAlarm);
						
						//adding the time of the alarm to the list of alarm time strings in the TimeKeeper
						TimeKeeper.arrayListOfAlarmTime.add(alarmTimeString);
						
						//setting the data of the jlist to display the list of alarm times
						jListOfAlarms.setListData(TimeKeeper.arrayListOfAlarmTime.toArray());
						
						//updating the alarm file
						updateFile();
						
					}catch(Exception e1) {
						JOptionPane.showMessageDialog(null, "Please only enter numbers", "Error", JOptionPane.ERROR_MESSAGE);
					}
					
					
					
				}else {
					
					JOptionPane.showMessageDialog(null, "Please enter a time for the alarm to ring.", "Error", JOptionPane.ERROR_MESSAGE);
					
				}
				
			}
		});
		add(btnAddAlarm);
		
		//delete button to delet alarms
		JButton btnDeleteAlarm = new JButton("Delete Alarm");
		btnDeleteAlarm.setBounds(300, 280, 100, 75);
		btnDeleteAlarm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//removing the alarm and the alarm times from the arraylists in the timekeeper, and updating the file
				TimeKeeper.arrayListOfAlarms.remove(jListOfAlarms.getSelectedIndex());
				
				TimeKeeper.arrayListOfAlarmTime.remove(jListOfAlarms.getSelectedIndex());
				
				jListOfAlarms.setListData(TimeKeeper.arrayListOfAlarmTime.toArray());
				
				updateFile();
			}
		});
		add(btnDeleteAlarm);
		
		//button us used to navigate back to the home screen
		JButton btnBackToHomeButton = new JButton("Back");
		btnBackToHomeButton.setBounds(250, 400, 100, 100);
		btnBackToHomeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AlarmMainJFrame.displayJPanel(AlarmMainJFrame.alarmHomeScreen);
			}
		});
		add(btnBackToHomeButton);
		
	}
	
	public void updateFile() {
		try {
			
			alarmWriter = new PrintWriter(alarmFile);
			
			for(Alarm alarmToWrite : TimeKeeper.arrayListOfAlarms) {
				
				String timeOfAlarmToWrite = alarmToWrite.strTimeToRing;
				
				int indexOfTone = 0;
				
				File toneOfAlarmToWrite = alarmToWrite.toneToPlayFile;
				
				for(int idx = 0; idx < toneFilesArray.length; idx++) {
					
					if(toneFilesArray[idx] == toneOfAlarmToWrite) {
						indexOfTone = idx;
					}
					
				}
				
				alarmWriter.println(timeOfAlarmToWrite + "-" + indexOfTone);
				alarmWriter.flush();
			}
			
			alarmWriter.close();
			
		}catch(Exception e) {
			
		}
	}

	public String readRawFileData() {
		
		
		
		String rawFileDataString = "";
		
		try {
			
			if(!alarmFile.exists()) {
				alarmFile.createNewFile();
			}
			
			alarmFileReader = new FileReader(alarmFile);
			
			int charInt;
			
			while((charInt = alarmFileReader.read())!= -1) {
				rawFileDataString += (char)charInt;
			}
			
		}catch(Exception e) {
			
		}
		
		return rawFileDataString;
		
		
	}

	
	
	
	
	
}
