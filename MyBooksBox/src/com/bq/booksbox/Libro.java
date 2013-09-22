package com.bq.booksbox;

import java.util.Date;

public class Libro{

	private String titulo;
	//private String autor;
	private Date fecha;
	
	public Libro(){
		
	}
	
	public Libro(String titulo, /*String autor,*/ Date fecha) {
		super();
		this.titulo = titulo;
		//this.autor = autor;
		this.fecha = fecha;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

/*	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}
*/
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
}
