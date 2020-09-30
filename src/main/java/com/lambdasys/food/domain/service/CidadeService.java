package com.lambdasys.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.lambdasys.food.domain.exceptions.CidadeNaoEncontradaException;
import com.lambdasys.food.domain.exceptions.EntidadeEmUsoException;
import com.lambdasys.food.domain.model.Cidade;
import com.lambdasys.food.domain.model.Estado;
import com.lambdasys.food.domain.repository.CidadeRepository;

@Service
public class CidadeService {

	private static final String MSG_REGISTRO_EM_USO = "Cidade de código %d não pode ser removida pois está em uso.";

	@Autowired
	private CidadeRepository repository;
	
	@Autowired
	private EstadoService estadoService;
	
	public Cidade findById( Long id ) {
		return this.repository.findById( id )
				.orElseThrow( () -> new CidadeNaoEncontradaException(id));
	}
	
	public Cidade save( Cidade cidade ) {
		Long estadoId = cidade.getEstado().getId();	
		Estado estado = estadoService.findById( estadoId );
		cidade.setEstado(estado);
		return repository.save( cidade );
	}
	
	public void delete( Long id ) {		
		try {
			repository.deleteById(id);
		}catch(EmptyResultDataAccessException ex) {
			throw new CidadeNaoEncontradaException(id);
		}catch(DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException(String.format(MSG_REGISTRO_EM_USO,id));
		}	
	}
	
}
