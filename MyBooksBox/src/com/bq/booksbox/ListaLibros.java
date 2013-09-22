package com.bq.booksbox;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class ListaLibros extends ArrayList<Libro> implements Parcelable{
	
	private static final long serialVersionUID = 1L;

	public ListaLibros(){
		
	}
	
	public ListaLibros(List<Libro> a){
		super(a);
	}
	public ListaLibros(Parcel p) {
		readFromParcel(p);	
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		int size = this.size();
		dest.writeInt(size);
		for (int i = 0; i < size; i++)
		{
		Libro l = (Libro) this.get(i);
		dest.writeString(l.getTitulo());
		//dest.writeString(l.getAutor());
		dest.writeSerializable(l.getFecha());	
		}		
	}
	
	private void readFromParcel(Parcel in){
		this.clear();
		//Leemos el tamaño del array int size = in.readInt();
		int size = in.readInt();
		for (int i = 0; i < size; i++){
			//el orden de los atributos importa
			Libro l = new Libro();
			l.setTitulo(in.readString()); 
			//l.setAutor(in.readString());
		 	l.setFecha((Date) in.readSerializable());
			this.add(l);
		}
	}
	
	 @SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {  
		    
	        public ListaLibros createFromParcel(Parcel p) {  
	            return new ListaLibros(p);  
	        }  
	        
	        public Libro[] newArray(int size){
	        	return new Libro[size];
			} 
	          
	}; 
	

}
