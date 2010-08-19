/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package meteodroid.apk;

import meteodroid.apk.meteodroid.UpdateService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Gravity;
import android.widget.Toast;

/**
 *
 * @author ferran
 */
public class meteoPref extends PreferenceActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);
	}
	
	public void onDestroy(){
        super.onDestroy();
        Toast toast = Toast.makeText(this, "Mapa actualitzat", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
        ConnectivityManager mConnectivity = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = mConnectivity.getActiveNetworkInfo();
        if (info != null && mConnectivity.getBackgroundDataSetting())
        	this.startService(new Intent(this, UpdateService.class));
        
	}

}