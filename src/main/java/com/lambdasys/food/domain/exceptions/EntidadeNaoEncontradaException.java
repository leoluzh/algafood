package com.lambdasys.food.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@SuppressWarnings("serial")
public abstract class EntidadeNaoEncontradaException extends ResponseStatusException {

	public EntidadeNaoEncontradaException( HttpStatus status , String mensagem ) {
		super( status , mensagem );
	}
	
	public EntidadeNaoEncontradaException( String mensagem ) {
		super( HttpStatus.NOT_FOUND , mensagem );
	}
	
}
