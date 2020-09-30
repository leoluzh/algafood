package com.lambdasys.food.domain.exceptions;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final String MSG_REGISTRO_NAO_EXISTE = "Não existe um cadastro de cidade com código %d";
	
	
	public CidadeNaoEncontradaException( HttpStatus status , String mensagem ) {
		super( status , mensagem );
	}
	
	public CidadeNaoEncontradaException( String mensagem ) {
		super( HttpStatus.NOT_FOUND , mensagem );
	}
	
	public CidadeNaoEncontradaException( Long id ) {
		this( String.format(MSG_REGISTRO_NAO_EXISTE , id ) );
	}
}
