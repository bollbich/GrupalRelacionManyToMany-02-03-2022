package com.formacionsprongboot.apirest.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formacionsprongboot.apirest.entity.Cliente;

@Repository
public interface ClienteDao extends CrudRepository<Cliente, Long>{

}
