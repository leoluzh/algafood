package com.lambdasys.food.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.lambdasys.food.domain.repository.queries.CidadeRepositoryQueries;

@Repository
public class CidadeRepositoryImpl implements CidadeRepositoryQueries {
	
	@PersistenceContext
	private EntityManager em;
	
}
