package com.lambdasys.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.lambdasys.food.domain.exceptions.EntidadeEmUsoException;
import com.lambdasys.food.domain.exceptions.EstadoNaoEncontradoException;
import com.lambdasys.food.domain.model.Estado;
import com.lambdasys.food.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso";
	
	@Autowired
	private EstadoRepository repository;
	
	public Estado save(Estado estado) {
		return repository.save(estado);
	}
	
	public void excluir(Long id) {
		try {
			repository.deleteById(id);			
		} catch (EmptyResultDataAccessException ex) {
			throw new EstadoNaoEncontradoException(id);
		
		} catch (DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException(
				String.format(MSG_ESTADO_EM_USO, id));
		}
	}	
	
	public Estado findById( Long id ) {
		return this.repository.findById( id ).orElseThrow( () -> new EstadoNaoEncontradoException( id ) );

	}
	
}
