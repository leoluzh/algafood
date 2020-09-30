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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lambdasys.food.domain.model.Estado;
import com.lambdasys.food.domain.repository.EstadoRepository;
import com.lambdasys.food.domain.service.EstadoService;

@RestController
@RequestMapping("/estados")
@SuppressWarnings("serial")
public class EstadoController implements Serializable {

	@Autowired
	private EstadoRepository repository;
	
	@Autowired
	private EstadoService service;
	
	@GetMapping
	public ResponseEntity<List<Estado>> list(){
		return ResponseEntity.ok( this.repository.findAll() );
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Estado> find( @PathVariable("id") Long id ){
		Estado estado = service.findById( id );
		return ResponseEntity.ok( estado );
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Estado> create( @Valid @RequestBody Estado estado ){
		return ResponseEntity.status(HttpStatus.CREATED).body( service.save( estado ) );
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Estado> update( @PathVariable("id") Long id , @Valid @RequestBody Estado estado ){
		Estado estadoAtual = service.findById( id );
		BeanUtils.copyProperties( estado , estadoAtual , "id" );
		estadoAtual = service.save( estadoAtual );
		return ResponseEntity.ok( estadoAtual );
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete( @PathVariable("id") Long id ){
		service.excluir( id );
		return ResponseEntity.noContent().build();
	}
	
}
