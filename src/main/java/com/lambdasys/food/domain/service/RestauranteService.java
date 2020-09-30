package com.lambdasys.food.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.lambdasys.food.domain.exceptions.CozinhaNaoEncontradaException;
import com.lambdasys.food.domain.exceptions.EntidadeEmUsoException;
import com.lambdasys.food.domain.exceptions.RestauranteNaoEncontradoException;
import com.lambdasys.food.domain.model.Cozinha;
import com.lambdasys.food.domain.model.Restaurante;
import com.lambdasys.food.domain.repository.CozinhaRepository;
import com.lambdasys.food.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	private static final String MSG_REGISTRO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso";
	
	
	@Autowired
	private RestauranteRepository repository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Restaurante save(Restaurante restaurante) {
		
		Long cozinhaId = restaurante.getCozinha().getId();
		
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
			.orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
		
		restaurante.setCozinha(cozinha);
		
		return repository.save(restaurante);
	}	
	
	public void excluir(Long id) {
		try {
			repository.deleteById(id);			
		} catch (EmptyResultDataAccessException ex) {
			throw new RestauranteNaoEncontradoException(id);
		
		} catch (DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException(
				String.format(MSG_REGISTRO_EM_USO, id));
		}
	}	
	
	public Restaurante findById( Long id ) {
		return this.repository.findById( id ).orElseThrow( () -> new RestauranteNaoEncontradoException( id ) );

	}
	
	
}
