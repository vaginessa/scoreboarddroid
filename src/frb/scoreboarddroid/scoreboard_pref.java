/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package frb.scoreboarddroid;

import java.util.Locale;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
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

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		// TODO Auto-generated method stub
		
		
		if( preference.getKey().equalsIgnoreCase("send_mail")){
			final Intent emailIntent = new Intent(Intent.ACTION_SEND); 			  
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject_info_mail)); 
			emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{"fribell@gmail.com"});
			emailIntent.setType("text/plain");
			startActivity(Intent.createChooser(emailIntent, getString(R.string.app_name)));
		}
		
		if( preference.getKey().equalsIgnoreCase("url")){
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse("http://ferran.ribell.com/ScoreBoardDroid"));
			startActivity(i);
		}

		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Resources res = getResources();
        Configuration conf = res.getConfiguration();
        DisplayMetrics dm = res.getDisplayMetrics();
        
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences p =   PreferenceManager.getDefaultSharedPreferences(this);        
        String loc = p.getString("idioma", "");        
        conf.locale = new Locale(loc);        
        res.updateConfiguration(conf, dm);
        
        Intent new_intent = new Intent(this, main.class);
        startActivity(new_intent);
	}
		
}