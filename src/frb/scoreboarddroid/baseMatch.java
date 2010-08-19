package frb.scoreboarddroid;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Chronometer.OnChronometerTickListener;


public class baseMatch extends Activity {
	
	public Integer TIME_MATCH = 30;
	public Integer MAX_PERIODE = 2;	
	static long tiempo = SystemClock.elapsedRealtime();
	static boolean running = false;
	static boolean fin = false;	
	static Calendar date_match = Calendar.getInstance();
	public Integer periode = 1;
	
	long elapsedTime=0;
	final Handler hnd = new Handler(); 
	
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
    }
    
    
    //funcion para iniciar los botones, cronometros, ...
    public void ini() {
        
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
        			if(seconds<0) seconds = 0;
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
        TextView but_periode = (TextView) this.findViewById(R.id.txt_period);
        but_periode.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		if(periode > 1){
	        		ImageButton but_crono = (ImageButton) findViewById(R.id.but_start);
	            	but_crono.setBackgroundResource(R.drawable.play);
	            	crono.stop();
	            	crono.setBase(SystemClock.elapsedRealtime());
	            	fin = false;
	            	currentTime = "";
	        		running = false;
	        		periode = 2;
	
	        		TextView but_periode = (TextView) findViewById(R.id.txt_period);
	        		but_periode.setText(Integer.parseInt(but_periode.getText().toString())+1);
        		}
        	}
        });
        
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
	    		hnd.post(new Runnable() { 
	    			public void run() {    			
	    				long minutes=((elapsedTime-crono.getBase())/1000)/60;
	    				//long seconds=((elapsedTime-crono.getBase())/1000)%60;
	        			if( minutes > TIME_MATCH && !fin && TIME_MATCH != 99){
	        				fin = true;
	        				endPeriod();        						
	        			}
					} 
				});
	    		
	    		if(fin) this.cancel();
    		}
    	}, 300, 1000 );
    	
    } 
    
    public void endPeriod(){
    	
		crono.stop();
		ImageButton but_crono = (ImageButton) findViewById(R.id.but_start);
		but_crono.setBackgroundResource(R.drawable.play);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.end_period);
		if(periode == MAX_PERIODE)			
	    	builder.setMessage(R.string.next_time);
		else			
	    	builder.setMessage(R.string.end_game);

    	builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					this.finalize();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}						
			}
    	});
	    	
    	AlertDialog alert = builder.create();
    	alert.show();
		
    }
    
    public void startStopMatch(View v){
    	ImageButton but_crono = (ImageButton) findViewById(R.id.but_start);
    	if(fin){
    		crono.stop();
    		but_crono.setBackgroundResource(R.drawable.play);
    	}else if(running){
    		crono.stop();
            currentTime = "1";
    		running = false;
    		but_crono.setBackgroundResource(R.drawable.play);
    	}else{
    		checkTime();
    		but_crono.setBackgroundResource(R.drawable.stop);
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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 0, 0, R.string.new_game).setIcon(R.drawable.new_game);
        //menu.add(0, 1, 0, R.string.save_game).setIcon(R.drawable.save_game);
        menu.add(0, 2, 0, R.string.send_game).setIcon(R.drawable.send_game);        
        menu.add(0, 3, 0, R.string.but_exit).setIcon(R.drawable.exit);
        
        return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        
    	TextView  pointsl = (TextView) findViewById(R.id.point_local);
    	TextView  pointsv = (TextView) findViewById(R.id.point_visitor);
    	TextView  txt_periode = (TextView) findViewById(R.id.txt_period);
        switch(item.getItemId()) {            

            case 0:            	
            	ImageButton but_crono = (ImageButton) findViewById(R.id.but_start);
            	but_crono.setBackgroundResource(R.drawable.play);
            	crono.stop();
            	crono.setBase(SystemClock.elapsedRealtime());
            	fin = false;
            	currentTime = "";
        		running = false;
            	
            	
            	pointsl.setText("0");            	
            	pointsv.setText("0");            	
            	txt_periode.setText("1");
            return true;

            case 1:

            return true;
            
            case 2:
            	
            	String txt = getString(R.string.time_match)+": "+crono.getBase()+"\n\r"; 
            	txt+= getString(R.string.local)+": "+pointsl.getText().toString()+"\n\r";
            	txt+= getString(R.string.visitor)+": "+pointsv.getText().toString()+"\n\r";
            	txt+= getString(R.string.period)+": "+txt_periode.getText().toString()+"\n\r";
            	
            	final Intent emailIntent = new Intent(Intent.ACTION_SEND); 			  
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject_mail)); 
				emailIntent.putExtra(Intent.EXTRA_TEXT, txt);
				emailIntent.setType("text/plain");
				startActivity(Intent.createChooser(emailIntent, getString(R.string.app_name)));
				      
            return true;

            case 3:
            	finish();
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }
}