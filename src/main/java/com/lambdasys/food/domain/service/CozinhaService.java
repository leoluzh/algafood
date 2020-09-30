package com.lambdasys.food.domain.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.lambdasys.food.domain.exceptions.CozinhaNaoEncontradaException;
import com.lambdasys.food.domain.exceptions.EntidadeEmUsoException;
import com.lambdasys.food.domain.model.Cozinha;
import com.lambdasys.food.domain.repository.CozinhaRepository;

@Service
@SuppressWarnings("serial")
public class CozinhaService implements Serializable {

	private static final String MSG_COZINNHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso.";
	
	@Autowired
	private CozinhaRepository repository;
	
	public Cozinha save( Cozinha cozinha ) {
		return this.repository.save( cozinha );
	}
	
	public void delete( Long id ) {
		try {
			repository.deleteById( id );
		}catch (EmptyResultDataAccessException ex) {
			throw new CozinhaNaoEncontradaException(id);
		}catch (DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException(String.format(MSG_COZINNHA_EM_USO, id));
		}
	}
	
	public Cozinha findById( Long id ) {
		return this.repository.findById( id ).orElseThrow( () -> new CozinhaNaoEncontradaException(id) );

	}
	
}
