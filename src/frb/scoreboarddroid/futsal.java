package frb.scoreboarddroid;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class futsal extends baseMatch {
	
	public Integer TIME_MATCH = 20;
	public Integer MAX_PERIODE = 2;
	public Integer faltes_local = 0;
	public Integer faltes_visitor = 0;
	
	static int periode = 1;
	
		 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.futsal);
        
        sport = "futsal";
        ini();
        
        ImageButton but_addfaltalocal = (ImageButton) this.findViewById(R.id.but_addfaltalocal);
        ImageButton but_addfaltavisitor = (ImageButton) this.findViewById(R.id.but_addfaltavisitor);
        
        but_addfaltalocal.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		faltes_local++;
        		TextView  falta = (TextView) findViewById(R.id.faltes_local);
        		falta.setText(getString(R.string.faltes)+' '+faltes_local.toString());
        		
        	}
        });
        but_addfaltavisitor.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		faltes_visitor++;
        		TextView  falta = (TextView) findViewById(R.id.faltes_visitor);
        		falta.setText(getString(R.string.faltes)+' '+faltes_visitor.toString());
        		
        	}
        });
    }
    
}