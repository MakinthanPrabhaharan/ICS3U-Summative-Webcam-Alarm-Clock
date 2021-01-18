
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
/*makinthan PRABHAHARAN
 * jUNE 11
 * */

//this class is used to check if it is time for any of the alarms to ring in the background
public class TimeKeeper {
	
	static ArrayList<Alarm> arrayListOfAlarms = new ArrayList<Alarm>();
	static ArrayList<String> arrayListOfAlarmTime = new ArrayList<String>();
	static Alarm currentAlarm = null;
	
	static String strCurrentDisplayTime = "";
	static String strCurrentExactTime = "";
	
	public TimeKeeper() {
		
		//Using a timer to check if it is time to ring the alarms every second, calling the tick method
		Timer mainTimer = new Timer();
		
		TimerTask tickTask = new TimerTask() {
			
			@Override
			public void run() {
				tickClock();
			}
		};
		
		mainTimer.schedule(tickTask, 0, 1000);
		
	}
	
	
	//this teick method is used to update the time strings and also checks if it is time for any of the alarms to ring
	public void tickClock() {
		
		LocalTime currentTime = LocalTime.now();
		
		DateTimeFormatter exactTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss a");
		
		DateTimeFormatter displayTimeFormatter = DateTimeFormatter.ofPattern("HH:mm a");
		
		strCurrentExactTime = currentTime.format(exactTimeFormatter);
		
		strCurrentDisplayTime = currentTime.format(displayTimeFormatter);
		
		for(Alarm alarm: arrayListOfAlarms) {
			
			if(alarm.strTimeToRing.equals(strCurrentExactTime)) {
				
				Thread thread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						currentAlarm = alarm;
						currentAlarm.keepPlaying = true;
						
						alarm.ring();
						
						AlarmMainJFrame.displayJPanel(AlarmMainJFrame.alarmRingingScreen);
						AlarmMainJFrame.alarmRingingScreen.playVideo();
						
					}
				});
				
				thread.start();
				
				
				
			}
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	

}
