package com.lambdasys.food.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	DADOS_INVALIDOS("/dados-invalidos","Dados inválidos.") ,
	ERRO_DE_SISTEMA("/erro-sistema","Erro de sistema"),
	PARAMETRO_INVALIDO("/parametro-invalido","Parametro inválido."),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel","Mensagem incompreensivel."),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrada","Recurso não encontrado."),
	ENTIDADE_EM_USO("/entidade-em-uso","Entidade em uso."),
	ERRO_NEGOCIO("/erro-negocio","Violação de regra de negócio.");


	ProblemType( String title , String uri ) {
		this.title = title ;
		this.uri = uri;
	}
	
	private String title;
	private String uri;
		
}
