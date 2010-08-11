package frb.scoreboarddroid;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.Chronometer.OnChronometerTickListener;


public class handball extends Activity {
	
	static long tiempo = SystemClock.elapsedRealtime();
	static boolean running = false;
	static boolean fin = false;
	long elapsedTime=0;
	
	TimerTask scanTask; 
    final Handler handler = new Handler(); 
    Timer t = new Timer(); 
    
	Chronometer crono;
	String currentTime="";
	Boolean resume=false;
	
	 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handballmatch);
        
        Typeface face=Typeface.createFromAsset(getAssets(), "fonts/DroidLogo.ttf");

        crono = (Chronometer) this.findViewById(R.id.crono);
        crono.setTypeface(face);
        crono.setOnChronometerTickListener(new OnChronometerTickListener(){
        	public void onChronometerTick(Chronometer arg0) {
        		long seconds = 0;
        		long minutes = 0;
        		if(currentTime.equalsIgnoreCase("")){
        			minutes=((SystemClock.elapsedRealtime()-crono.getBase())/1000)/60;
        			seconds=((SystemClock.elapsedRealtime()-crono.getBase())/1000)%60;
        			if(minutes < 10) currentTime = "0"+minutes+":";
        			else currentTime = minutes+":";
        			if(seconds<10) currentTime = currentTime+"0"+seconds;
        			else currentTime = currentTime+seconds;
        			elapsedTime=SystemClock.elapsedRealtime();
        		}else{
        			minutes=((elapsedTime-crono.getBase())/1000)/60;
        			seconds=((elapsedTime-crono.getBase())/1000)%60;
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
        
        //evento click de los botones
        ImageButton but_addLocal = (ImageButton) this.findViewById(R.id.but_addLocal);
        ImageButton but_removeLocal = (ImageButton) this.findViewById(R.id.but_removeLocal);
        ImageButton but_addVisitor = (ImageButton) this.findViewById(R.id.but_addVisitor);
        ImageButton but_removeVisitor = (ImageButton) this.findViewById(R.id.but_removeVisitor);
        but_addLocal.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		TextView  points = (TextView) findViewById(R.id.point_local);
        		changePoints(points,"+");
        	}
        });
        
        but_removeLocal.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		TextView  points = (TextView) findViewById(R.id.point_local);
        		changePoints(points,"-");
        	}
        });
        
        but_addVisitor.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		TextView  points = (TextView) findViewById(R.id.point_visitor);
        		changePoints(points,"+");
        	}
        });
        
        but_removeVisitor.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		TextView  points = (TextView) findViewById(R.id.point_visitor);
        		changePoints(points,"-");
        	}
        });
        
        

       
    }

    public void checkTime(){ 

    	t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
        		long seconds=((elapsedTime-crono.getBase())/1000)%60;
    			Log.d("TIMER", "aaaaaaaaa"+seconds);
    			if(seconds > 5){
    				//fin = true;
    				ToggleButton but_crono = (ToggleButton) findViewById(R.id.but_start);
    				crono.stop();   				
    				but_crono.setChecked(true);
    				this.cancel();
    				
    			}
            }
    	}, 300, 1000 );
    	
    	/*
    	scanTask = new TimerTask() { 
    		public void run() {
    			long seconds=((elapsedTime-crono.getBase())/1000)%60;
    			Log.d("TIMER", "aaaaaaaaa"+seconds);
    			if(seconds == 5){
    				//fin = true;
    				ToggleButton but_crono = (ToggleButton) findViewById(R.id.but_start);
    				but_crono.performClick();
    				getParent();
    				//but_crono.toggle();
    				
    			}
				 
    		}}; 


    		t.schedule(scanTask, 300, 1000);*/ 
    } 
    
    public void startStopMatch(View v){
    	
    	if(fin){
    		crono.stop();
    	}else if(running){
    		crono.stop();
            currentTime = "1";
    		running = false;
    	}else{
    		checkTime();
    		if(currentTime.equalsIgnoreCase("")){
    			crono.setBase(SystemClock.elapsedRealtime());
    			crono.start();
    		}else{
    			crono.start();
    		}
  
    		running = true;
    	}
    }
    
    public void changePoints(TextView points, String op){
    	Integer result = 0;
    	if( op.equalsIgnoreCase("+"))
    		result = Integer.parseInt(points.getText().toString())+1;
    	else if(!points.getText().toString().equalsIgnoreCase("0"))
			result = Integer.parseInt(points.getText().toString())-1;
    	
		points.setText(result.toString());
    	
    }
    
}