package com.lambdasys.food.api.controller;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdasys.food.core.validation.ValidacaoException;
import com.lambdasys.food.domain.exceptions.EntidadeNaoEncontradaException;
import com.lambdasys.food.domain.exceptions.NegocioException;
import com.lambdasys.food.domain.model.Restaurante;
import com.lambdasys.food.domain.repository.RestauranteRepository;
import com.lambdasys.food.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes")
@SuppressWarnings("serial")
public class RestauranteController implements Serializable {

	@Autowired
	private RestauranteRepository repository;
	
	@Autowired
	private RestauranteService service;
	
	@Autowired
	private SmartValidator validator;
	 
	@GetMapping
	public ResponseEntity<List<Restaurante>> list(){
		return ResponseEntity.ok( repository.findAll() );
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurante> find( @PathVariable("id") Long id ){
		Restaurante restaurante = service.findById( id );
		return ResponseEntity.ok( restaurante );
	}
	
	@PostMapping
	public ResponseEntity<?> create( @Valid @RequestBody Restaurante restaurante ){
		try {
			restaurante = service.save(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		}catch(EntidadeNaoEncontradaException ex) {
			throw new NegocioException(ex.getMessage(),ex.getCause());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update( @PathVariable("id") Long id , @Valid @RequestBody Restaurante restaurante ){
		try {
			Restaurante restauranteAtual = service.findById(id);
			BeanUtils.copyProperties( restaurante , restauranteAtual , "id" , "formasPagamento" , "endereco" , "produtos" );
			restauranteAtual = service.save(restauranteAtual);
			return ResponseEntity.ok(restauranteAtual);
		}catch(EntidadeNaoEncontradaException ex) {
			throw new NegocioException(ex.getMessage(),ex.getCause());
		}
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> patch( @PathVariable("id") Long id , @RequestBody Map<String,Object> campos ){
		try {
			Restaurante restauranteAtual = service.findById( id );
			merge( campos , restauranteAtual );
			validate(restauranteAtual);
			return update( id , restauranteAtual );
		}catch(EntidadeNaoEncontradaException ex) {
			throw new NegocioException(ex.getMessage(),ex.getCause());
		}
	}
	
	protected void validate( Restaurante restaurante ) {
		BeanPropertyBindingResult br = new BeanPropertyBindingResult( restaurante ,"restaurante");
		validator.validate( restaurante , br );
		if( br.hasErrors() ) {
			throw new ValidacaoException(br);
		}
	}
	
	private void merge( Map<String,Object> campos , Restaurante destino ) {
		ObjectMapper mapper = new ObjectMapper();
		Restaurante origem = mapper.convertValue( campos , Restaurante.class );
		campos.forEach( ( campo , valor ) -> {
			Field field = ReflectionUtils.findField( Restaurante.class , campo );
			field.setAccessible(true);
			Object novoValor = ReflectionUtils.getField( field , origem );
			ReflectionUtils.setField(field, destino , valor );
		});
	}
	
}
