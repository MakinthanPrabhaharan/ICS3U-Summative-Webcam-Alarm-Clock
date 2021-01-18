import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

/*Makinthan PRABHAHARAN
 * June 11
 * Summative Program
 * */

//this is the class that is the jpanel that displays when the alarm actually rings.
//the user must retrieve the image that they print, and show it to the camera.  if it is the corerc image, the alarm dismisses.
/*it uses somethign called template matching.  It is when a smaller image is compared to another larger image.  The algorithm slides
 * the template image around the larger image, to attempt and find the specific image.*/
public class AlarmRingingScreen extends JPanel {
	
	//label used to show the image that the user must show to the camera
	JLabel imageToScanLabel = new JLabel();
	
	//label used to show the video input from teh camera
	JLabel cameraVideoLabel = new JLabel();
	
	//videocaptuer objecty used from opencv, which will represent the camera
	VideoCapture videoCapture = null;
	
	public AlarmRingingScreen() {
		
		//loading the opencv library
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		//initializng the videocapture object
		videoCapture = new VideoCapture();
		
		//setting the size, color an dlayout of the panel
		setSize(600, 600);
		setBackground(Color.ORANGE);
		setLayout(null);
		
		//setting the location of the image to scan label 
		imageToScanLabel.setBounds(250, 0, 100, 100);
		add(imageToScanLabel);
		
		//setting the locaiton and size of the video label
		cameraVideoLabel.setBounds(0, 100, 600, 360);
		add(cameraVideoLabel);
		
		
	}
	
	//method used to display the video from teh camera
	public void playVideo() {
		
		//array of objects that holds the template image mat object and its respective threshold
		Object templateAndThresholdObject[] = getTemplateAndThreshold();
		
		//getting the template image and thre rehsholds from the object array
		Mat templateImage = (Mat) templateAndThresholdObject[0];
		double threshold = (double) templateAndThresholdObject[1];
		
		//checking if the camera is not already opened, before opening it
		if(!videoCapture.isOpened()) {
			videoCapture.open(0);
		}
		
		//infinite while loop to keep displaying the camera frames
		while(true) {
			
			//initializing a Mat to hold the frame from the camera
			Mat cameraFrame = new Mat();
			
			//passing the cameraFrame into the videoCapture.read method of the VideoCapture.  It stores th input of the camera in the cameraFrame matrix (Mat)
			videoCapture.read(cameraFrame);
			
			//flipping the camera frame horzontally to be displayed
			Core.flip(cameraFrame, cameraFrame, Core.ROTATE_180);
			

			
			//MAt used to store the result of the template matching
			Mat templateMatchingResult = new Mat();
			
			//using the matchtemplate image to match the template image to whatevers in the frame from teh camera
			Imgproc.matchTemplate(cameraFrame, templateImage, templateMatchingResult, Imgproc.TM_CCORR_NORMED);
			
			//this result variable will hold the max matching value that is found from the template matching result matreix
			Core.MinMaxLocResult maxLocResult = Core.minMaxLoc(templateMatchingResult);
			
			//this value will be comapred to the threshold
			double maxMatchingValue = maxLocResult.maxVal;
			
			//if that is greater than the threshold, teh alarm stops
			if(maxMatchingValue > threshold) {
				
				TimeKeeper.currentAlarm.keepPlaying = false;
				
				AlarmMainJFrame.displayJPanel(AlarmMainJFrame.alarmHomeScreen);
				
				videoCapture.release();
				
			}
			
			//displaying the video frame ont the label
			Image frameImage = HighGui.toBufferedImage(cameraFrame);
			
			ImageIcon frameIcon = new ImageIcon(frameImage);
			
			cameraVideoLabel.setIcon(frameIcon);
			
		}
		
	}
	
	//this function is used to get the random template image and its respective threshold value
	public Object[] getTemplateAndThreshold() {
		
		//Mat variables representing the template images
		Mat deltaMat = Imgcodecs.imread(AlarmMainJFrame.GAMEFILES + "vectordeltaIcon.png");
		Mat airplaneMat = Imgcodecs.imread(AlarmMainJFrame.GAMEFILES + "airplaneIcon.png");
		Mat starMat = Imgcodecs.imread(AlarmMainJFrame.GAMEFILES + "blankstar.png");
		//array of template images
		Mat arrayOfMats[] = {deltaMat, airplaneMat, starMat};
		
		//hashmap used to map each template image to its respective thereholsd
		HashMap<Mat, Double> matAndThresholdHashMap = new HashMap<Mat, Double>();
		matAndThresholdHashMap.put(deltaMat, 0.98);
		matAndThresholdHashMap.put(airplaneMat, 0.94);
		matAndThresholdHashMap.put(starMat, 0.97);
		
		//array of imageicons
		ImageIcon iconsOfTemplates[] = {new ImageIcon(AlarmMainJFrame.GAMEFILES + "vectordeltaIcon.png"), new ImageIcon(AlarmMainJFrame.GAMEFILES + "airplaneIcon.png"), new ImageIcon(AlarmMainJFrame.GAMEFILES + "blankstar.png")};
		
		//generating a random index to select a tempalte image from teh array
		int randomIndex = new Random().nextInt(3);
		
		//random template image
		Mat randomTemplateImage = arrayOfMats[randomIndex];
		
		//threhold form the hashmap and the random template image
		double threshold = matAndThresholdHashMap.get(randomTemplateImage);
		
		//setting the imagetoscan label to the icon of the template image from the array of icons
		imageToScanLabel.setIcon(iconsOfTemplates[randomIndex]);
		
		//declaring and returning the array of template images and threshodl
		Object templateAndThreshold[] = {randomTemplateImage,threshold};
		
		return templateAndThreshold;
		
	}
	
	
	
	
	
	
	
	
}
