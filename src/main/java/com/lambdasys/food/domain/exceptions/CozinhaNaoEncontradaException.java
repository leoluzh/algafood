package com.lambdasys.food.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@SuppressWarnings("serial")
public class CozinhaNaoEncontradaException extends ResponseStatusException {

	private static final String MSG_REGISTRO_NAO_EXISTE = "Não existe um cadastro de cozinha com código %d";
	
	
	public CozinhaNaoEncontradaException( HttpStatus status , String mensagem ) {
		super( status , mensagem );
	}
	
	public CozinhaNaoEncontradaException( String mensagem ) {
		super( HttpStatus.NOT_FOUND , mensagem );
	}
	
	public CozinhaNaoEncontradaException( Long id ) {
		this( String.format(MSG_REGISTRO_NAO_EXISTE, id) );
	}
	
	
}
