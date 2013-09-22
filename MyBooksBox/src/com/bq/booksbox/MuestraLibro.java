package com.bq.booksbox;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class MuestraLibro extends Activity {
	
	private  ListaLibros listaLibros = new ListaLibros();
	private int posicion;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.muestra_libro);
		
		Bundle contenedor=getIntent().getExtras();
		listaLibros = contenedor.getParcelable("lista");
		posicion = contenedor.getInt("pos");
		
		Libro libro = listaLibros.get(posicion);

        TextView text_titulo = (TextView) findViewById(R.id.titulo);
        text_titulo.setText(libro.getTitulo());
        
        TextView text_fecha = (TextView) findViewById(R.id.fecha);
        Date f = libro.getFecha();
		SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy kk:mm");
		text_fecha.setText((fecha.format(f)).toString());
        
        ImageView icono =(ImageView) findViewById(R.id.iconolibro);
        icono.setImageResource(R.drawable.book);
       
	}
	
	@SuppressLint("NewApi")
	public void onBackPressed(){
        super.onBackPressed();
        Intent intentListar = new Intent(this, ListarContenido.class);  
    	Bundle contenedor = new Bundle();
    	contenedor.putParcelable("lista", listaLibros); 
    	intentListar.putExtras(contenedor);
    	startActivity(intentListar); 
    	this.finish();
	}
	public void onPause(){
	    super.onPause();
	}

}
