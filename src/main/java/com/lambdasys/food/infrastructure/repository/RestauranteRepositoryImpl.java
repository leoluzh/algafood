package com.lambdasys.food.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.lambdasys.food.domain.model.Restaurante;
import com.lambdasys.food.domain.model.Restaurante_;
import com.lambdasys.food.domain.repository.RestauranteRepository;
import com.lambdasys.food.domain.repository.queries.RestauranteRepositoryQueries;
import com.lambdasys.food.domain.repository.specs.RestauranteSpecs;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	@Lazy
	private RestauranteRepository respository;

	@Override
	public List<Restaurante> find(String nome, 
			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		
		var builder = em.getCriteriaBuilder();
		
		var criteria = builder.createQuery(Restaurante.class);
		var root = criteria.from(Restaurante.class);

		var predicates = new ArrayList<Predicate>();
		
		if (StringUtils.hasText(nome)) {
			predicates.add(builder.like(root.get(Restaurante_.nome), "%" + nome + "%"));
		}
		
		if ( Objects.nonNull( taxaFreteInicial ) ) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Restaurante_.taxaFrete), taxaFreteInicial));
		}
		
		if ( Objects.nonNull( taxaFreteFinal ) ) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Restaurante_.taxaFrete), taxaFreteFinal));
		}
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		var query = em.createQuery(criteria);
		
		return query.getResultList();
		
	}	
	
	@Override
	public List<Restaurante> findAllComFrenteGratis() {
		return respository.findAll(RestauranteSpecs.comFreteGratis());
	}

	@Override
	public List<Restaurante> findAllComNomeSemelhante(String nome) {
		return respository.findAll(RestauranteSpecs.comNomeSemelhante(nome));
	}
	
}
