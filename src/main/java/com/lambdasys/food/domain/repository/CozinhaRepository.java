package com.lambdasys.food.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.lambdasys.food.domain.model.Cozinha;
import com.lambdasys.food.domain.repository.custom.CustomJpaRepository;
import com.lambdasys.food.domain.repository.queries.CozinhaRepositoryQueries;

@Repository
public interface CozinhaRepository 
	extends CustomJpaRepository<Cozinha,Long> , 
			CozinhaRepositoryQueries , 
			JpaSpecificationExecutor<Cozinha> {

}
