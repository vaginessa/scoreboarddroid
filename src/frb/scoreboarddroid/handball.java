package frb.scoreboarddroid;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;


public class handball extends Activity {
	
	static long tiempo = SystemClock.elapsedRealtime();
	static boolean running = false;
	long elapsedTime=0;
	
	Chronometer crono;
	String currentTime="";
	Boolean resume=false;
	
	 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handballmatch);
        
        crono = (Chronometer) this.findViewById(R.id.crono);
        crono.setOnChronometerTickListener(new OnChronometerTickListener(){
        	public void onChronometerTick(Chronometer arg0) {
        		if(currentTime.equalsIgnoreCase("")){
        			long minutes=((SystemClock.elapsedRealtime()-crono.getBase())/1000)/60;
        			long seconds=((SystemClock.elapsedRealtime()-crono.getBase())/1000)%60;
        			if(minutes < 10) currentTime = "0"+minutes+":";
        			else currentTime = minutes+":";
        			if(seconds<10) currentTime = currentTime+"0"+seconds;
        			else currentTime = currentTime+seconds;
        			elapsedTime=SystemClock.elapsedRealtime();
        		}else{
        			long minutes=((elapsedTime-crono.getBase())/1000)/60;
        			long seconds=((elapsedTime-crono.getBase())/1000)%60;
        			currentTime=minutes+":"+seconds;
        			if(minutes < 10) currentTime = "0"+minutes+":";
        			else currentTime = minutes+":";
        			if(seconds<10) currentTime = currentTime+"0"+seconds;
        			else currentTime = currentTime+seconds;
        			elapsedTime=elapsedTime+1000;
        		}
        		arg0.setText(currentTime);
        	}
        });
        
    }
    
    public void startStopMatch(View v){
    	if(running){
    		crono.stop();
            currentTime = "1";
    		running = false;
    	}else{
    		if(currentTime.equalsIgnoreCase("")){
    			crono.setBase(SystemClock.elapsedRealtime());
    			crono.start();
    		}else{
    			crono.start();
    		}
  
    		running = true;
    	}
    }
    
}