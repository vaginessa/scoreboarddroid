package frb.scoreboarddroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class main extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void startMatch(View v){
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle(R.string.select_sport);
    	builder.setItems(R.array.esports,new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int item) {
    	    	String[] items = getResources().getStringArray(R.array.esports);

    	        if( items[item].equalsIgnoreCase("balonmano") ){
	            	Intent new_intent = new Intent(frb.scoreboarddroid.main.this, handball.class);
	                new_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                startActivity(new_intent);	     
    	        }
    	        
    	        if( items[item].equalsIgnoreCase("Voleibol") ){
	            	Intent new_intent = new Intent(frb.scoreboarddroid.main.this, beachvoley.class);
	                new_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                startActivity(new_intent);	     
    	        }
    	        
    	    }
    	});
    	AlertDialog alert = builder.create();
    	alert.show();    	
    }
    
    public void exit(View v){
        finish();
    }
    
}