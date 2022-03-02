package com.formacionsprongboot.apirest.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formacionsprongboot.apirest.entity.Compra;

@Repository
public interface CompraDao extends CrudRepository<Compra, Long> {

}
