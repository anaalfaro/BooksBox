package com.bq.booksbox;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


@SuppressLint("SimpleDateFormat")
public class ListarContenido extends Activity{
	
	private ListaLibros listaLibros = new ListaLibros();
	private ListView listaView;
	private View ordenAlf, ordenFecha;
	private AdaptadorItem adaptadorLista;
	private Libro [] arrayLibros;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.listar_contenido);
		
		Bundle contenedor=getIntent().getExtras();
		listaLibros = contenedor.getParcelable("lista");

		ordenAlf = findViewById(R.id.fondo1);
		ordenAlf.setOnClickListener(new View.OnClickListener() {         
            public void onClick(View v) {
            	arrayLibros = listaLibros.toArray(new Libro[listaLibros.size()]);
            	ordenarQuicksortAlfabeticamente(arrayLibros, 0, arrayLibros.length-1);
            	List<Libro> list = Arrays.asList(arrayLibros);
            	listaLibros = new ListaLibros(list);
            	adaptadorLista.notifyDataSetChanged();
            	
            }
        });
	     
		ordenFecha = findViewById(R.id.fondo2);
		ordenFecha.setOnClickListener(new View.OnClickListener() {         
            public void onClick(View v) {
            	arrayLibros = listaLibros.toArray(new Libro[listaLibros.size()]);
            	ordenarQuicksortFecha(arrayLibros, 0, arrayLibros.length-1);
            	List<Libro> list = Arrays.asList(arrayLibros);
            	listaLibros = new ListaLibros(list);
            	adaptadorLista.notifyDataSetChanged();
            }
        });
		
		adaptadorLista = new AdaptadorItem(this);
	    listaView = (ListView)findViewById(R.id.lista);	            
	    listaView.setAdapter(adaptadorLista);
	    
	        
	    listaView.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
	        	
	             Libro libro_selec = (Libro)a.getAdapter().getItem(position);
	             lanzarMuestraLibro(libro_selec, position);	             
	            }	
	        });
	}
	
	private void lanzarMuestraLibro(Libro libro_selec, int pos) {
        Intent intentMostrar = new Intent(this, MuestraLibro.class);
        Bundle contenedor=new Bundle();
        contenedor.putParcelable("lista", listaLibros);
        contenedor.putInt("pos", pos);
        intentMostrar.putExtras(contenedor);
        startActivity(intentMostrar); 
        this.finish();		
	}
	
	public void ordenarQuicksortAlfabeticamente(Libro [] array, int izq, int der){
	       if(izq < der) {
	           int k = particionarAlfab(array, izq, der);
	           ordenarQuicksortAlfabeticamente(array, izq, k);
	           ordenarQuicksortAlfabeticamente(array, k+1 , der);
	       }
	}
	 
	public int particionarAlfab(Libro[] array, int izq, int der) {
	        Libro pivote = array[izq];
	        int i = izq, j = der;
	        Libro auxiliar=new Libro();
	        while (i < j) {
	            while (arrayLibros[i].getTitulo().compareToIgnoreCase(pivote.getTitulo())<0 && i < der) {
	                i++;
	            }
	            while (arrayLibros[j].getTitulo().compareToIgnoreCase(pivote.getTitulo())>0 && j > izq) {
	                j--;
	            } 
	 
	            if(i == izq && j == i){
	            	ordenarQuicksortAlfabeticamente(array, izq + 1, der);
	            }
	            if(i == der && j == i)
	            	ordenarQuicksortAlfabeticamente(array, izq, der - 1);
	 
	            if (i < j) {
	            	auxiliar.setTitulo(arrayLibros[j].getTitulo());
					auxiliar.setFecha(arrayLibros[j].getFecha());
					arrayLibros[j].setTitulo(arrayLibros[i].getTitulo());
					arrayLibros[j].setFecha(arrayLibros[i].getFecha());
					arrayLibros[i].setTitulo(auxiliar.getTitulo());
					arrayLibros[i].setFecha(auxiliar.getFecha());
	            }
	        }
	        return j;
	    }
	 
	public void ordenarQuicksortFecha(Libro [] array, int izq, int der){
	       if(izq < der) {
	           int k = particionarFecha(array, izq, der);
	           ordenarQuicksortFecha(array, izq, k);
	           ordenarQuicksortFecha(array, k+1 , der);
	       }
	}
	 
	public int particionarFecha(Libro[] array, int izq, int der) {
	        Libro pivote = array[izq];
	        int i = izq, j = der;
	        Libro auxiliar=new Libro();
	        while (i < j) {
	            while (array[i].getFecha().compareTo(pivote.getFecha())<0 && i < der) {
	                i++;
	            }
	            while (array[j].getFecha().compareTo(pivote.getFecha())>0 && j > izq) {
	                j--;
	            } 
	 
	            if(i == izq && j == i){
	            	ordenarQuicksortFecha(array, izq + 1, der);
	            }
	            if(i == der && j == i)
	            	ordenarQuicksortFecha(array, izq, der - 1);
	 
	            if (i < j) {
	            	auxiliar.setTitulo(array[j].getTitulo());
					auxiliar.setFecha(array[j].getFecha());
					array[j].setTitulo(array[i].getTitulo());
					array[j].setFecha(array[i].getFecha());
					array[i].setTitulo(auxiliar.getTitulo());
					array[i].setFecha(auxiliar.getFecha());
	            }
	        }
	        return j;
	    }  
	


	
	public class AdaptadorItem extends ArrayAdapter<Libro>{
		private Activity context;
	
		public AdaptadorItem(Activity context) {
			super(context, R.layout.item_listar, listaLibros);
			this.context = context;
		}
	
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View item = inflater.inflate(R.layout.item_listar, null);
			
			ImageView icono =(ImageView) item.findViewById(R.id.iconoitem);
			
			TextView labelTitulo = (TextView) item.findViewById(R.id.titulo);
			labelTitulo.setText(listaLibros.get(position).getTitulo());
			
			TextView labelFecha = (TextView) item.findViewById(R.id.fecha);

			icono.setImageResource(R.drawable.book);
			Date f = listaLibros.get(position).getFecha();
			SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy kk:mm");
			labelFecha.setText((fecha.format(f)).toString());

			return(item);
	    }
	}

}