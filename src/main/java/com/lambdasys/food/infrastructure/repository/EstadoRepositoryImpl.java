package com.lambdasys.food.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.lambdasys.food.domain.repository.queries.EstadoRepositoryQueries;

@Repository
public class EstadoRepositoryImpl implements EstadoRepositoryQueries {

	@PersistenceContext
	private EntityManager em;
	
}
