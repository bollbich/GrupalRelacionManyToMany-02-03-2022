package com.formacionsprongboot.apirest.productos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formacionsprongboot.apirest.productos.dao.ProductoDao;
import com.formacionsprongboot.apirest.productos.entity.Producto;

@Service
public class ProductoServiceImpl implements ProductoService{

	@Autowired
	ProductoDao AccesoDb;
	
	@Override
	public List<Producto> ListarTodosProductos() {

		return (List<Producto>) AccesoDb.findAll();
	}

	@Override
	public Producto FinById(Long id) {
		
		return AccesoDb.findById(id).orElse(null);
	}

	@Override
	public Producto save(Producto producto) {
		
		return AccesoDb.save(producto);
	}

	@Override
	public void Delete(Long id) {

		AccesoDb.deleteById(id);
		
	}

}
