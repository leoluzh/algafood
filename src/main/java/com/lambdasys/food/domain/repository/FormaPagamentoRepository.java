package com.lambdasys.food.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.lambdasys.food.domain.model.FormaPagamento;
import com.lambdasys.food.domain.repository.custom.CustomJpaRepository;
import com.lambdasys.food.domain.repository.queries.FormaPagamentoRepositoryQueries;

@Repository
public interface FormaPagamentoRepository 
	extends CustomJpaRepository<FormaPagamento,Long> , 
			FormaPagamentoRepositoryQueries , 
			JpaSpecificationExecutor<FormaPagamento>{

}
