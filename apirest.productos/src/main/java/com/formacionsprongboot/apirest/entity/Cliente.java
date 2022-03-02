package com.formacionsprongboot.apirest.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="productos")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="codigo", nullable = false, length=50)
	private String codigo;
	
	@Column(name="tipo", nullable = false, length=50)
	private String tipo;
	
	@Column(name="cantidad", nullable = false, length=50)
	private int cantidad;
	
	@Column(name="precio", nullable = false)
	private double precio;
	
	@Column(name="marca", nullable = false, length=50)
	private String marca;
	
	@Column(name="fecha_ingreso")
	@Temporal(TemporalType.DATE)
	private Date fecha_ingreso;
	
	@Column(name="descripcion", nullable = false, length=250)
	private String descripcion;
	
	@Column(name="imagen", nullable = true)
	private String imagen;
	
	
	@PrePersist
	public void prePersist()
	{
		if(fecha_ingreso==null)
		this.setFecha_ingreso(new Date());
	}
	
	private static final long serialVersionUID = 1L;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Date getFecha_ingreso() {
		return fecha_ingreso;
	}

	public void setFecha_ingreso(Date fecha_ingreso) {
		this.fecha_ingreso = fecha_ingreso;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	
	
	

}
