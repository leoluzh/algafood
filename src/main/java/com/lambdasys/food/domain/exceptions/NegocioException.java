package com.lambdasys.food.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@SuppressWarnings("serial")
public class NegocioException extends ResponseStatusException {

	public NegocioException( String mensagem ) {
		super(HttpStatus.BAD_REQUEST,mensagem);
	}

	public NegocioException( String mensagem , Throwable cause ) {
		super(HttpStatus.BAD_REQUEST,mensagem,cause);
	}
	
	
	public NegocioException(HttpStatus status, String reason, Throwable cause) {
		super(status, reason, cause);
	}

	public NegocioException(HttpStatus status, String reason) {
		super(status, reason);
	}

	public NegocioException(HttpStatus status) {
		super(status);
	}

}