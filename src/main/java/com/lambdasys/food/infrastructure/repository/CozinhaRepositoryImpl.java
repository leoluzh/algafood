package com.lambdasys.food.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.lambdasys.food.domain.repository.queries.CozinhaRepositoryQueries;

@Repository
public class CozinhaRepositoryImpl implements CozinhaRepositoryQueries {

	@PersistenceContext
	private EntityManager em;
	
}
