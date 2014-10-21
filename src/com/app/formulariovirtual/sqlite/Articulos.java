package com.app.formulariovirtual.sqlite;

public class Articulos {

	private int id;
	private String nombre;
	private String tipo;
	private String color;
	private String marca;
	private String cantidad;

	public Articulos() {
		// TODO Auto-generated constructor stub
	}

	// constructor
	public Articulos(int id, String nombre, String tipo, String color,
			String marca, String cantidad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.color = color;
		this.marca = marca;
		this.cantidad = cantidad;
	}

	// constructor
	public Articulos(String nombre, String tipo, String color, String marca,
			String cantidad) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.color = color;
		this.marca = marca;
		this.cantidad = cantidad;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

}
