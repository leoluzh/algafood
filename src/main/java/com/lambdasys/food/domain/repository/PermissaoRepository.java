package com.lambdasys.food.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.lambdasys.food.domain.model.Permissao;
import com.lambdasys.food.domain.repository.custom.CustomJpaRepository;
import com.lambdasys.food.domain.repository.queries.PermissaoRepositoryQueries;

@Repository
public interface PermissaoRepository 
	extends CustomJpaRepository<Permissao,Long> , 
			PermissaoRepositoryQueries ,
			JpaSpecificationExecutor<Permissao>{

}
