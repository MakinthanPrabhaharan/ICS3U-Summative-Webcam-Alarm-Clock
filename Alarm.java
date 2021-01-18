import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/*makinthan PRABHAHARAN
 * jUNE 11
 * */

//this class represents the Alarm.  It has a ring method that sounds the alarm when it is called.
public class Alarm {
	
	String strTimeToRing = "";
	
	File toneToPlayFile = null;
	
	boolean keepPlaying = true;
	
	
	//constructor takes the time for it to ring as a string, and the tone that it plays as a file
	public Alarm(String strTimeToRing, File toneToPlay) {
		this.strTimeToRing = strTimeToRing;
		this.toneToPlayFile = toneToPlay;
	}
	
	//ringing the alarm, playing the sound file passed in as a parameter
	public void ring() {
		
		Thread ringThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					Clip clip = AudioSystem.getClip();
					
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(toneToPlayFile);
					
					clip.open(audioInputStream);
					
					do {
						clip.loop(Clip.LOOP_CONTINUOUSLY);
					} while (keepPlaying);
					
					clip.stop();
					
				} catch (Exception e) {
					
				}
			
			}
		});
		
		ringThread.start();
		
	}

}