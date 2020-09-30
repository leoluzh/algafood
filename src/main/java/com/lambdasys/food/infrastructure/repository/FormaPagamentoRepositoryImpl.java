package com.lambdasys.food.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.lambdasys.food.domain.repository.queries.FormaPagamentoRepositoryQueries;

@Repository
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepositoryQueries {

	@PersistenceContext
	private EntityManager em;
	
}


