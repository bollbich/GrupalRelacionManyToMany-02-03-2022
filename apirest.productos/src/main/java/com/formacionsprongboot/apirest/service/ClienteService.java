package com.formacionsprongboot.apirest.service;

import java.util.List;

import com.formacionsprongboot.apirest.entity.Cliente;


public interface ClienteService {
	
	public List<Cliente> ListarTodosClientes();
	
	public Cliente FinById(Long id);
	
	public Cliente save(Cliente cliente);
	
	public void Delete(Long id);

}
