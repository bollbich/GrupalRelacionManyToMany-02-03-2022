package com.formacionsprongboot.apirest.service;

import java.util.List;

import com.formacionsprongboot.apirest.entity.Articulo;



public interface ArticuloService {
		
		public List<Articulo> ListarTodosArticulos();
		
		public Articulo FinById(Long id);
		
		public Articulo save(Articulo articulo);
		
		public void Delete(Long id);

}
