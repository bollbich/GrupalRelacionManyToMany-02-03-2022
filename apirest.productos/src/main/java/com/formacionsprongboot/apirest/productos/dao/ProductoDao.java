package com.formacionsprongboot.apirest.productos.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formacionsprongboot.apirest.productos.entity.Producto;

@Repository
public interface ProductoDao extends CrudRepository<Producto, Long>{

}
