package frb.scoreboarddroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class tenis extends baseMatch {
	
	public Integer TIME_MATCH = 99;//quiere decir que no tiene limite de tiempo
	public Integer MAX_PERIODE = 5;
	public Integer tiebreak = 0; //0:no hi ha tibreak //1:es juga tibreak //2:diferencia menor a 2 pero no es juga tibreak
	
		 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenis);
        
        sport = "tenis";
        ini();
        TextView but_periode = (TextView) this.findViewById(R.id.txt_period);
        but_periode.setOnClickListener(null);
        
    }
    
    /*
     * M�todo dibujarTabla
     * Devuelve una TableLayout con borde
     * recibe:
     *  tama�o del borde - int
     *  numero de filas - int
     *  numero de columnas - int
     *  color en Hexadecimal #FFFFFF - String
     */
    public TableLayout dibujarTabla(int tamBorde, int numeroFilas, int numeroColumnas, String colorBorde,String point_local,String point_visitor, String timeset){
    	TableLayout tabla = new TableLayout(this);
      
		if(numeroFilas>0 && numeroColumnas>0){
		  TableRow fila = new TableRow(this);
		  int numeroCeldas = numeroFilas * numeroColumnas;
		   
		  /* Calculo el Ancho de la siguiente manera:
		   * El ancho de la pantalla los divido entre el n� de columnas y le resto
		   * el tama�o del borde que esta sumado al borde dividido entre el n� de columnas
		   * (a causa del borde derecho de la ultima columna)
		   */		   
		  int ancho=(getWindowManager().getDefaultDisplay().getWidth()/numeroColumnas)-(tamBorde+(tamBorde/(numeroColumnas)));
		  //No se porqu� pero si le resto uno funciona...
		  ancho--;
		   
		  int contadorColumnas=0;
		  int contadorFilas=0;
		   
		  for (int i = 0; i <= numeroCeldas; i++) {
			  //Si Ya ha dibujado la cantidad de columnas
			  if(contadorColumnas==numeroColumnas){
				  tabla.addView(fila);
				  fila = new TableRow(this);
				  contadorColumnas=0;
				  contadorFilas++;			    
			  }
			   
			  //Definimos los bordes de la tabla
			  RelativeLayout borde = new RelativeLayout(this);
			  //Dibuja los de arriba y la izquierda siempre
			  borde.setPadding(tamBorde,tamBorde,0,0);
			  //Pero
			  //Si ya es la ultima columna de la fila...
			  if(contadorColumnas==numeroColumnas-1){
			   //Dibuja los de arriba a la derecha e izquierda.
			   borde.setPadding(tamBorde, tamBorde, tamBorde, 0);
			  }
			  //Si Es la ultima fila
			  if(contadorFilas==numeroFilas-1){
				  //Dibuja arriba, izquierda y abajo
				   borde.setPadding(tamBorde,tamBorde,0,tamBorde);
				   //Si ademas de ser la ultima fila es la ultima columna
				   if(contadorColumnas==(numeroColumnas)-1){
					   //Dibuja todos los lados
					   borde.setPadding(tamBorde,tamBorde,tamBorde,tamBorde);
				   }
			  }
			  //Color del borde.
			  borde.setBackgroundColor(Color.parseColor(colorBorde));
			   
			  //Ahora el texto..
			  TextView texto = new TextView(this);
			  if(i == 0)
				  texto.setText(point_local);
			  else if(i == 1)
				  texto.setText(point_visitor);
			  else
				  texto.setText(timeset);
			  
			  texto.setTextColor(Color.WHITE);
	          //texto.setWidth(ancho);
	          texto.setGravity(Gravity.CLIP_HORIZONTAL);
	          texto.setPadding(2, 2, 2, 2);
	          //Importante el color, porque si no se ver� de color del borde!!
			  texto.setBackgroundColor(Color.BLACK);
			   
			  //Al borde le a�adimos el texto
			  borde.addView(texto);
			   
			  //Y a la fila el borde con el texto
	          fila.addView(borde);
	          contadorColumnas++;
		     
		   }
		  
	     }else{
	    	 TextView error= new TextView(this);
	         error.setText("Valores de columnas o filas deben ser mayor de 0");
	         tabla.addView(error);
	     }
	     return tabla;
    }
    
    public void changePoints(TextView points, String op){
    	Integer result = 0;
    	TextView  pointsl = (TextView) findViewById(R.id.point_local);
		TextView  pointsv = (TextView) findViewById(R.id.point_visitor);
		TextView  setl = (TextView) findViewById(R.id.set_local);
		TextView  setv = (TextView) findViewById(R.id.set_visitor);
		
    	if(tiebreak == 1){    		
    		if( op.equalsIgnoreCase("+")){
        		result = Integer.parseInt(points.getText().toString())+1;
        		points.setText(result.toString());
        		if(result > 6) winset();
    		}else if(!points.getText().toString().equalsIgnoreCase("0")){
    			result = Integer.parseInt(points.getText().toString())-1;
    			points.setText(result.toString());
    		}
   		
    	}
    	
		if( points.getText().toString().equalsIgnoreCase("0") ||
    		points.getText().toString().equalsIgnoreCase("15")){
    		
    		if( op.equalsIgnoreCase("+"))
        		result = Integer.parseInt(points.getText().toString())+15;
        	else if(!points.getText().toString().equalsIgnoreCase("0"))
    			result = Integer.parseInt(points.getText().toString())-15;
    		
    		points.setText(result.toString());
    	}else if( points.getText().toString().equalsIgnoreCase("30") ){
    		
    		if( op.equalsIgnoreCase("+"))
        		result = 40;
        	else
    			result = 15;
    		points.setText(result.toString());
    		
    		if( pointsl.getText().toString().equalsIgnoreCase( pointsv.getText().toString() ) ){
                Toast toast = Toast.makeText(this, "DEUCE", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
    		}
    		
    	}else if( points.getText().toString().equalsIgnoreCase("40") ){
    		if( op.equalsIgnoreCase("+")){
    			if(pointsl.getText().toString().equalsIgnoreCase("AV")){
    				pointsl.setText("40");
    			}else if(pointsv.getText().toString().equalsIgnoreCase("AV")){
    				pointsv.setText("40");
    			}else if(pointsl.getText().toString().equalsIgnoreCase( pointsv.getText().toString() )){
    				points.setText("AV");
    			}else{
    	    		if(pointsl.getText().toString().equalsIgnoreCase("40")){
    	    			result = Integer.parseInt(setl.getText().toString())+1;
    	    			setl.setText(result.toString());
    	    		}else{
    	    			result = Integer.parseInt(setv.getText().toString())+1;
    	    			setv.setText(result.toString());
    	    		}    				
    	    		pointsl.setText("0");
    	    		pointsv.setText("0");
    	    		winset();
    			}
    		}else
    			points.setText("30");
    	}else if( points.getText().toString().equalsIgnoreCase("AV") ){
    		if( op.equalsIgnoreCase("+")){
    			if(pointsl.getText().toString().equalsIgnoreCase("AV")){
	    			result = Integer.parseInt(setl.getText().toString())+1;
	    			setl.setText(result.toString());
	    		}else{
	    			result = Integer.parseInt(setv.getText().toString())+1;
	    			setv.setText(result.toString());
	    		}    			
	    		pointsl.setText("0");
	    		pointsv.setText("0");
	    		winset();
	    		
    		}else
    			points.setText("40");
    	}
    	
    }
    
    public void winset(){
    	TextView  setl = (TextView) findViewById(R.id.set_local);
		TextView  setv = (TextView) findViewById(R.id.set_visitor);
		TextView  pointsl = (TextView) findViewById(R.id.point_local);
		TextView  pointsv = (TextView) findViewById(R.id.point_visitor);
		
		Integer dif = 0;
		
		if(tiebreak == 1){
			dif = Integer.parseInt(pointsl.getText().toString()) - Integer.parseInt(pointsv.getText().toString());
			if(dif >= 2 || dif <= -2){
				tiebreak = 0;
				Integer result = 0;
				if(pointsl.getText().toString().equalsIgnoreCase("AV")){
	    			result = Integer.parseInt(setl.getText().toString())+1;
	    			setl.setText(result.toString());
	    		}else{
	    			result = Integer.parseInt(setv.getText().toString())+1;
	    			setv.setText(result.toString());
	    		}    			
	    		pointsl.setText("0");
	    		pointsv.setText("0");
	    		saveSet();
			}
		}else if((Integer.parseInt(setl.getText().toString())==6 &&
				Integer.parseInt(setv.getText().toString())==6) && tiebreak != 1){
			
		
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("TieBreak");
	    	builder.setMessage(R.string.txt_tiebreak);
	    	builder.setNegativeButton(R.string.ko, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					try {
						tiebreak = 2;
						this.finalize();
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}						
				}
	    	});
	    	builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					try {
						tiebreak = 1;
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}						
				}
	    	});
	    	AlertDialog alert = builder.create();
	    	alert.show();
		}else if((Integer.parseInt(setl.getText().toString())>=6 ||
				Integer.parseInt(setv.getText().toString())>=6) && tiebreak != 1){
			
			dif = Integer.parseInt(setl.getText().toString()) - Integer.parseInt(setv.getText().toString());
			if(dif == 1 || dif == -1){
				
				
			}else if(tiebreak == 2){
				dif = Integer.parseInt(setl.getText().toString()) - Integer.parseInt(setv.getText().toString());
				if(dif >= 2 || dif <= -2){
					saveSet();
				}
			}else{
				saveSet();
			}
			
		}
		    	
    }
    
    public void saveSet(){
    	TextView  setl = (TextView) findViewById(R.id.set_local);
		TextView  setv = (TextView) findViewById(R.id.set_visitor);
		TextView  txtperiod = (TextView) findViewById(R.id.txt_period);
		ImageButton but_crono = (ImageButton) findViewById(R.id.but_start);
		
    	long minutes = 0;
    	long seconds = 0;
    	if(currentTime.equalsIgnoreCase("")){
			minutes=0;
			seconds=0;
    	}else{
    		minutes=((elapsedTime-crono.getBase())/1000)/60;
			seconds=((elapsedTime-crono.getBase())/1000)%60;		
		}
    	
		LinearLayout ll = (LinearLayout) findViewById(R.id.resultados);        		
		TableLayout etiquetaTabla = dibujarTabla(1,3,1,"#FFFFFF",
		setl.getText().toString(),
		setv.getText().toString(),
		minutes+":"+seconds);
		ll.addView(etiquetaTabla);
		
		result_local+= setl.getText().toString()+",";
		result_visitor+= setv.getText().toString()+",";
		result_time+= minutes+":"+seconds+",";
		
		setl.setText("0");
		setv.setText("0");
		Integer set = Integer.parseInt(txtperiod.getText().toString())+1;
		txtperiod.setText(set.toString());
		but_crono.setBackgroundResource(R.drawable.play);
		crono.stop();
    	crono.setBase(SystemClock.elapsedRealtime());
    	fin = false;
    	currentTime = "";
		running = false;
    }
}