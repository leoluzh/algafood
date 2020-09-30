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

import com.lambdasys.food.domain.model.Cozinha;
import com.lambdasys.food.domain.repository.CozinhaRepository;
import com.lambdasys.food.domain.service.CozinhaService;

@RestController
@RequestMapping("/cozinhas")
@SuppressWarnings("serial")
public class CozinhaController implements Serializable {

	@Autowired
	private CozinhaRepository repository;
	
	@Autowired
	private CozinhaService service;
	
	@GetMapping
	public List<Cozinha> list(){
		return this.repository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> find( @PathVariable("id") Long id ) {
		Cozinha cozinha = service.findById(id);
		return ResponseEntity.ok(cozinha);
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED )
	public Cozinha salvar( @Valid @RequestBody Cozinha cozinha ) {
		return this.service.save( cozinha );
	}

	@PutMapping(path ="/{id}")
	public ResponseEntity<Cozinha> update( @PathVariable("id") Long id , @Valid @RequestBody Cozinha cozinha ) {
		Cozinha original = service.findById( id );
		BeanUtils.copyProperties( cozinha , original , "id" );
		Cozinha cozinhaSalva = this.service.save( original );
		return ResponseEntity.ok( cozinhaSalva );
	}
	
	@DeleteMapping(path="/{id}")
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> remove( @PathVariable("id") Long id ){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
}
