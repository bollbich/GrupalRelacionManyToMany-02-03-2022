package com.formacionsprongboot.apirest.service;

import java.util.Date;
import java.util.List;

import com.formacionsprongboot.apirest.entity.Compra;

public interface CompraService {

	public List<Compra> findAll();
	
	public Compra findById(Long id);
	
	public Compra save(Compra cliente);
	
	public Compra findByDate(Date date);
}
