package com.lambdasys.food.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.lambdasys.food.domain.model.Estado;
import com.lambdasys.food.domain.repository.custom.CustomJpaRepository;
import com.lambdasys.food.domain.repository.queries.EstadoRepositoryQueries;

@Repository
public interface EstadoRepository 
	extends CustomJpaRepository<Estado,Long> , 
			EstadoRepositoryQueries ,
			JpaSpecificationExecutor<Estado> {

}
