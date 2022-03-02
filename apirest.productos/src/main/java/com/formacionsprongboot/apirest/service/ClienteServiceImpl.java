package com.formacionsprongboot.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formacionsprongboot.apirest.dao.ClienteDao;
import com.formacionsprongboot.apirest.entity.Cliente;

@Service
public class ClienteServiceImpl implements ClienteService{
	@Autowired
	ClienteDao AccesoDb;
	
	@Override
	public List<Cliente> ListarTodosClientes() {

		return (List<Cliente>) AccesoDb.findAll();
	}

	@Override
	public Cliente FinById(Long id) {
		
		return AccesoDb.findById(id).orElse(null);
	}

	@Override
	public Cliente save(Cliente cliente) {
		
		return AccesoDb.save(cliente);
	}

	@Override
	public void Delete(Long id) {

		AccesoDb.deleteById(id);
		
	}
}
