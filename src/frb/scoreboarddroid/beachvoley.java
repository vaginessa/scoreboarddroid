package frb.scoreboarddroid;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class beachvoley extends baseMatch {
	
	public Integer TIME_MATCH = 99;//quiere decir que no tiene limite de tiempo
	public Integer MAX_PERIODE = 5;
	
		 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beachvoleymatch);
        
        ini();
        
        TextView but_periode = (TextView) this.findViewById(R.id.txt_period);
        
        but_periode.setOnClickListener(new OnClickListener() {
        	
        	public void onClick(View v) {
        		
        		periode++;
        		TextView but_periode = (TextView) findViewById(R.id.txt_period);
        		but_periode.setText(periode+"");
        		
        		TextView  pointsl = (TextView) findViewById(R.id.point_local);
        		TextView  pointsv = (TextView) findViewById(R.id.point_visitor);
        		
        		LinearLayout ll = (LinearLayout) findViewById(R.id.resultados);        		
        		TableLayout etiquetaTabla = dibujarTabla(1,2,1,"#FFFFFF",
        				pointsl.getText().toString(),
        				pointsv.getText().toString());
                ll.addView(etiquetaTabla);
    		
                pointsl.setText("0");
        		pointsv.setText("0");
        	}
        });    
        
    }
    
    /*
     * Método dibujarTabla
     * Devuelve una TableLayout con borde
     * recibe:
     *  tamaño del borde - int
     *  numero de filas - int
     *  numero de columnas - int
     *  color en Hexadecimal #FFFFFF - String
     */
    public TableLayout dibujarTabla(int tamBorde, int numeroFilas, int numeroColumnas, String colorBorde,String point_local,String point_visitor){
    	TableLayout tabla = new TableLayout(this);
      
		if(numeroFilas>0 && numeroColumnas>0){
		  TableRow fila = new TableRow(this);
		  int numeroCeldas = numeroFilas * numeroColumnas;
		   
		  /* Calculo el Ancho de la siguiente manera:
		   * El ancho de la pantalla los divido entre el nº de columnas y le resto
		   * el tamaño del borde que esta sumado al borde dividido entre el nº de columnas
		   * (a causa del borde derecho de la ultima columna)
		   */		   
		  int ancho=(getWindowManager().getDefaultDisplay().getWidth()/numeroColumnas)-(tamBorde+(tamBorde/(numeroColumnas)));
		  //No se porqué pero si le resto uno funciona...
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
			  else
				  texto.setText(point_visitor);
			  
			  texto.setTextColor(Color.WHITE);
	          //texto.setWidth(ancho);
	          texto.setGravity(Gravity.CLIP_HORIZONTAL);
	          texto.setPadding(2, 2, 2, 2);
	          //Importante el color, porque si no se verá de color del borde!!
			  texto.setBackgroundColor(Color.BLACK);
			   
			  //Al borde le añadimos el texto
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
    
}