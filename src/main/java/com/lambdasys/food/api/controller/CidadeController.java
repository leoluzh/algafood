package com.lambdasys.food.api.controller;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lambdasys.food.domain.exceptions.EstadoNaoEncontradoException;
import com.lambdasys.food.domain.exceptions.NegocioException;
import com.lambdasys.food.domain.model.Cidade;
import com.lambdasys.food.domain.repository.CidadeRepository;
import com.lambdasys.food.domain.service.CidadeService;

@RestController
@RequestMapping("/cidades")
@SuppressWarnings("serial")
public class CidadeController implements Serializable {

	@Autowired
	private CidadeRepository repository;
	
	@Autowired
	private CidadeService service;
	
	@GetMapping
	public List<Cidade> list(){
		return repository.findAll();
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Cidade> find( @PathVariable("id") Long id ){
		Cidade cidade = this.service.findById(id);
		return ResponseEntity.ok(cidade);
	}
	
	@PostMapping
	public ResponseEntity<?> create( @Valid @RequestBody Cidade cidade ){
		try {
			cidade = this.service.save( cidade );
			return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
		}catch(EstadoNaoEncontradoException ex) {
			throw new NegocioException(ex.getMessage(),ex.getCause());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update( @PathVariable("id") Long id , @Valid @RequestBody Cidade cidade ){
		try {
		Cidade cidadeAtual = service.findById( id );
		BeanUtils.copyProperties( cidade , cidadeAtual , "id" );
		cidadeAtual = service.save( cidadeAtual );
		return ResponseEntity.ok( cidadeAtual );
		}catch(EstadoNaoEncontradoException ex) {
			throw new NegocioException(ex.getMessage(),ex.getCause());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Cidade> delete( @PathVariable("id") Long id ){
		service.delete( id );
		return ResponseEntity.noContent().build();
	}
	
}
