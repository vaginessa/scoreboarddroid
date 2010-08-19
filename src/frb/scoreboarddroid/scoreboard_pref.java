/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package frb.scoreboarddroid;

import java.util.Locale;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 *
 * @author ferran
 */
public class scoreboard_pref extends PreferenceActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);
	}
	
	public void onDestroy(){
        super.onDestroy();
        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences p =   PreferenceManager.getDefaultSharedPreferences(this);
        String loc = p.getString("idiomes", "");
        
        conf.locale = new Locale(loc);
        DisplayMetrics dm = res.getDisplayMetrics();
        Log.d("IDIOMA", this.getResources().getConfiguration().locale.getDisplayLanguage().toString());
        Log.d("IDIOMA", this.getResources().getConfiguration().locale.getLanguage().toString());
        res.updateConfiguration(conf, dm);
        
        
	}

}