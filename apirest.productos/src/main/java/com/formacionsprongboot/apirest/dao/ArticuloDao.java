package com.formacionsprongboot.apirest.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formacionsprongboot.apirest.entity.Articulo;

@Repository
public interface ArticuloDao extends CrudRepository<Articulo, Long>{

	public Articulo findByNombre(String nombre);
}
