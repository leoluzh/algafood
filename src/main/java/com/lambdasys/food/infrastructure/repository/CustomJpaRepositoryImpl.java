package com.lambdasys.food.infrastructure.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.lambdasys.food.domain.repository.custom.CustomJpaRepository;

public class CustomJpaRepositoryImpl<T,ID> 
	extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID> {
	
	private EntityManager em;

	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.em = entityManager;
	}

	@Override
	public Optional<T> findFirst() {
		var jpql = "from " + getDomainClass().getName();
		T entity = this.em.createQuery( jpql , getDomainClass() ).setMaxResults(1).getSingleResult();
		return Optional.ofNullable(entity);
	}

}
