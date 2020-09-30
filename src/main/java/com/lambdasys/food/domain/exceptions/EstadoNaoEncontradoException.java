package com.lambdasys.food.domain.exceptions;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final String MSG_REGISTRO_NAO_EXISTE = "Não existe um cadastro de estado com código %d";
		
	public EstadoNaoEncontradoException( HttpStatus status , String mensagem ) {
		super( status , mensagem );
	}
	
	public EstadoNaoEncontradoException( String mensagem ) {
		super( HttpStatus.NOT_FOUND , mensagem );
	}

	public EstadoNaoEncontradoException( Long id ) {
		this( String.format(MSG_REGISTRO_NAO_EXISTE, id) );
	}
		
}
