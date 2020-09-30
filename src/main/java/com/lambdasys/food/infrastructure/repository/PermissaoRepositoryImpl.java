package com.lambdasys.food.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.lambdasys.food.domain.repository.queries.PermissaoRepositoryQueries;

@Repository
public class PermissaoRepositoryImpl implements PermissaoRepositoryQueries {
	
	@PersistenceContext
	private EntityManager em;

}
