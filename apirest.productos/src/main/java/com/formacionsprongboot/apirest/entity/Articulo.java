package com.formacionsprongboot.apirest.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="articulos")
public class Articulo {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long CodArticulo;
	
	@Column(name="nombre", nullable = false, length=50)
	private String nombre;
	
	@Column(name="descripcion", nullable = false, length=50)
	private String descripcion;
	
	@Column(name="precio_unidad", nullable = false, length=50)
	private double precio_unidad;
	
	@Column(name="stock", nullable = false)
	private int stock;
	
	@Column(name="stock_seguridad", nullable = false, length=50)
	private int stock_seguridad;
	
	@Column(name="imagen", nullable = true)
	private String imagen;
		
	private static final long serialVersionUID = 1L;
	
	public Long getCodArticulo() {
		return CodArticulo;
	}

	public void setCodArticulo(Long codArticulo) {
		CodArticulo = codArticulo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio_unidad() {
		return precio_unidad;
	}

	public void setPrecio_unidad(double precio_unidad) {
		this.precio_unidad = precio_unidad;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getStock_seguridad() {
		return stock_seguridad;
	}

	public void setStock_seguridad(int stock_seguridad) {
		this.stock_seguridad = stock_seguridad;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}


	
	
	

}
