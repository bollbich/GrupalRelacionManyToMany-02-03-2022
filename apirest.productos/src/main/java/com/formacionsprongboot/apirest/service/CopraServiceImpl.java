package com.formacionsprongboot.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionsprongboot.apirest.dao.CompraDao;
import com.formacionsprongboot.apirest.entity.Compra;

@Service
public class CopraServiceImpl implements CompraService {

	@Autowired
	private CompraDao compraDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Compra> findAll() {
		return (List<Compra>) compraDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Compra findById(Long id) {
		return compraDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Compra save(Compra compra) {
		return compraDao.save(compra);
	}
	/*
	@Override
	@Transactional
	public void delete(Long id) {
		compraDao.deleteById(id);
	}
	*/
}
