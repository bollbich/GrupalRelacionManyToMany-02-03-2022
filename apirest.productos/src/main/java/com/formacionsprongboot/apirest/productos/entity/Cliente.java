package com.formacionsprongboot.apirest.productos.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class Clientes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codCliente;
	
	@Column(name="apellido", nullable = false, length=50)
	private String nombre;
	
	@Column(name="apellido", nullable = false, length=50)
	private String apellido;
	
	@Column(name="empresa", nullable = false, length=50)
	private String empresa;
	
	@Column(name="puesto", nullable = false)
	private String puesto;
	
	@Column(name="cp", nullable = false, length=50)
	private int cp;
	
	@Column(name="telefono", nullable = false, length=50)
	private int telefono;
	
	@Column(name="fechaNacimiento")
	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;
	

	public Long getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(Long codCliente) {
		this.codCliente = codCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public int getCp() {
		return cp;
	}

	public void setCp(int cp) {
		this.cp = cp;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	
}
