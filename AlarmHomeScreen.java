
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
/*makinthan PRABHAHARAN
 * jUNE 11
 * */

//this is the class for the home screen jpanel of the alarm clock.
/*it displays the current time, and allows the user to navigate to the alarm manager screen, as
 * well as allowing the user to print out the template images that they need to shut the alarm off when it rings*/

public class AlarmHomeScreen extends JPanel {
	
	//this is the constructor for the home screen
	public AlarmHomeScreen() {
		
		//setting the size, background colour, and layout for the panel
		setSize(600, 600);
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		//creating a border object that will be used on the components in the home screen
		Border blueBorder = BorderFactory.createLineBorder(Color.BLUE, 2);
		
		//setting the location, size, font ,colour,alignment, and border of the label that is used to display the current time
		JLabel timeDisplayLabel = new JLabel();
		timeDisplayLabel.setBounds(100, 200, 400, 120);
		timeDisplayLabel.setFont(new Font("impact", Font.PLAIN, 96));
		timeDisplayLabel.setForeground(Color.BLUE);
		timeDisplayLabel.setHorizontalAlignment(JLabel.CENTER);
		timeDisplayLabel.setBorder(blueBorder);
		//adding the label to the home screen
		add(timeDisplayLabel);
		
		//this thread is used so that the time can be updated forever in teh background, without causing any lagging for the user interface
		Thread clockUpdateThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				//using an infinite while loop so that the label can be constantly updated
				while(true) {
					//putting the display time string from the TimeKeeper class into the label, which holds the current time in "HH:mm a" format
					timeDisplayLabel.setText(TimeKeeper.strCurrentDisplayTime);
				}
			}
		});
		//starting the thread that updates the time
		clockUpdateThread.start();
		
		//setting the text, location, size, colour, border for the button that lets the user navigate to the manage alarms screen
		JButton btnManageAlarmsButton = new JButton("Manage Alarms");
		btnManageAlarmsButton.setBounds(100, 330, 200, 75);
		btnManageAlarmsButton.setForeground(Color.BLUE);
		btnManageAlarmsButton.setBorder(blueBorder);
		btnManageAlarmsButton.setBackground(Color.LIGHT_GRAY);
		//adding an actionlistener to the manage alarms button
		btnManageAlarmsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//calling the static displayJPanel method from the AlarmMainJFrame class to display the alarmManagerScreen
				AlarmMainJFrame.displayJPanel(AlarmMainJFrame.alarmManagerScreen);
			}
		});
		//adding the button to the JPanel
		add(btnManageAlarmsButton);
		
		//setting the size, location, foreground, border, background for the button used to print out the template images
		JButton btnPrintTemplateImages = new JButton("Print Templates");
		btnPrintTemplateImages.setBounds(300, 330, 200, 75);
		btnPrintTemplateImages.setForeground(Color.BLUE);
		btnPrintTemplateImages.setBorder(blueBorder);
		btnPrintTemplateImages.setBackground(Color.LIGHT_GRAY);
		//adding an actionlistener to the print images button
		btnPrintTemplateImages.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//initializing an array of files that hold the files of the template images
				File imageFilesToPrint[] = {new File(AlarmMainJFrame.GAMEFILES + "blankstar.png"), new File(AlarmMainJFrame.GAMEFILES + "airplaneIcon.png"), new File(AlarmMainJFrame.GAMEFILES + "vectordeltaIcon.png")};
				
				//displaying a joption confirm dialog to ask the user if they want to print
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to print?") == 0) {
					
					try {
						
						
						for(int i = 0; i < imageFilesToPrint.length; i++) {
							
							//looping through each file in the array, and converting them to a buffered image, and then printing them by passing it into the method
							BufferedImage bufferedPrintImage = ImageIO.read(imageFilesToPrint[i]);
							printTemplateImage(bufferedPrintImage);
							
						}
						
						
					}catch(Exception e1) {
						
						//if something goes wrong while reading the images, it displays an error message
						JOptionPane.showMessageDialog(null, "Error reading images", "Error", JOptionPane.ERROR_MESSAGE);
					
					}
				}
				
				
				
			}
		});
		
		//adding the print template images button to the jpanel
		add(btnPrintTemplateImages);
		
		
	}
	
	//this method is used to print an image, and it takes in a BufferedImage as a parameter, which it will then print out
	public void printTemplateImage(BufferedImage image) {
		
		//creating a new PrinterJob object
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		
		//setting the printable interface for the printerjob
		printerJob.setPrintable(new Printable() {
			
			//this is the print method from the Printable interface. it is what actually prints the image
			@Override
			public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
				
				if(pageIndex != 0) {
					return NO_SUCH_PAGE;
				}
				
				//putting the image onto the paper, specifying the image, coordinates, and imageobserver, which is null
				graphics.drawImage(image, 200, 200, null);
				
				return PAGE_EXISTS;
			}
		});
		
		//using a try catch statement to actually print out the image
		try {
			
			printerJob.print();
			
		}catch(Exception e) {
			
			//displaying an error message in case something goes wrong while printing
			JOptionPane.showMessageDialog(null, "Printing Error", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}

}


