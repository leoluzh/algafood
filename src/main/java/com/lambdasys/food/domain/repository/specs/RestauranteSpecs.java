package com.lambdasys.food.domain.repository.specs;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.lambdasys.food.domain.model.Restaurante;
import com.lambdasys.food.domain.model.Restaurante_;

@SuppressWarnings("serial")
public class RestauranteSpecs implements Serializable {

	public static Specification<Restaurante> comNome( String nome ){
		return ( root , query , builder ) -> 
			builder.equal( root.get(Restaurante_.nome), nome);
	}
	
	public static Specification<Restaurante> comTaxaFreteMaiorQue( BigDecimal taxaFrete ){
		return ( root , query , builder ) ->
			builder.greaterThanOrEqualTo( root.get(Restaurante_.taxaFrete) , taxaFrete );
	}
	
	public static Specification<Restaurante> comTaxaFreteMenorQue( BigDecimal taxaFrete ){
		return ( root , query , builder ) ->
			builder.lessThanOrEqualTo( root.get(Restaurante_.taxaFrete), taxaFrete );
	}
	
	public static Specification<Restaurante> comFreteGratis() {
		return ( root , query , builder ) -> 
			builder.equal( root.get(Restaurante_.taxaFrete) , BigDecimal.ZERO );
	}
	
	public static Specification<Restaurante> comNomeSemelhante( String nome ){
		return ( root , query , builder ) -> 
			builder.like( root.get(Restaurante_.nome) , "%" + nome + "%" );
	}
	
	
}
