package com.lambdasys.food.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@SuppressWarnings("serial")
public class EntidadeEmUsoException extends ResponseStatusException {

	public EntidadeEmUsoException( HttpStatus status , String mensagem ) {
		super( status , mensagem );
	}
	
	public EntidadeEmUsoException( String mensagem ){
		super( HttpStatus.CONFLICT , mensagem );
	}
}
