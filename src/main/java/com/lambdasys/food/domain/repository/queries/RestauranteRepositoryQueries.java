package com.lambdasys.food.domain.repository.queries;

import java.math.BigDecimal;
import java.util.List;

import com.lambdasys.food.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal );
	public List<Restaurante> findAllComFrenteGratis();
	public List<Restaurante> findAllComNomeSemelhante( String nome );
	
}