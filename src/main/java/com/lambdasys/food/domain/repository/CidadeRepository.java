package com.lambdasys.food.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.lambdasys.food.domain.model.Cidade;
import com.lambdasys.food.domain.repository.custom.CustomJpaRepository;
import com.lambdasys.food.domain.repository.queries.CidadeRepositoryQueries;

@Repository
public interface CidadeRepository 
	extends CustomJpaRepository<Cidade,Long> , 
			CidadeRepositoryQueries ,
			JpaSpecificationExecutor<Cidade>{

}
