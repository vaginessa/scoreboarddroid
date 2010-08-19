package frb.scoreboarddroid;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;

public class main extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences p =   PreferenceManager.getDefaultSharedPreferences(this);
        String loc = p.getString("idiomes", "");
        if( !loc.equalsIgnoreCase("")){
        	Resources res = getResources();
            Configuration conf = res.getConfiguration();          
            DisplayMetrics dm = res.getDisplayMetrics();
            res.updateConfiguration(conf, dm);
        	conf.locale = new Locale(loc);
        }
        setContentView(R.layout.main);
        
    }
    
    public void startMatch(View v){    	
        
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle(R.string.select_sport);
    	builder.setItems(R.array.esports,new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int item) {
    	    	//String[] items = getResources().getStringArray(R.array.esports);

    	        if( item == 0 ){
	            	Intent new_intent = new Intent(frb.scoreboarddroid.main.this, handball.class);
	                new_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                startActivity(new_intent);	     
    	        }
    	        
    	        if( item == 1 ){
	            	Intent new_intent = new Intent(frb.scoreboarddroid.main.this, beachvoley.class);
	                new_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                startActivity(new_intent);	     
    	        }
    	        
    	    }
    	});
    	AlertDialog alert = builder.create();
    	alert.show();    	
    }
    
    public void openPreferences(View v){
    	Intent new_intent = new Intent(this, scoreboard_pref.class);
        new_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(new_intent);
    }
    
    public void exit(View v){
        finish();
    }
    
}