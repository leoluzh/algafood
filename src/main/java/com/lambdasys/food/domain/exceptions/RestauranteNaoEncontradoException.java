package com.lambdasys.food.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@SuppressWarnings("serial")
public class RestauranteNaoEncontradoException extends ResponseStatusException {

	private static final String MSG_REGISTRO_NAO_EXISTE = "Não existe um cadastro de restaurante com código %d";
	
	public RestauranteNaoEncontradoException( HttpStatus status , String mensagem ) {
		super( status , mensagem );
	}
	
	public RestauranteNaoEncontradoException( String mensagem ) {
		super( HttpStatus.NOT_FOUND , mensagem );
	}

	public RestauranteNaoEncontradoException( Long id ) {
		this( String.format( MSG_REGISTRO_NAO_EXISTE , id ) );
	}
		
}
