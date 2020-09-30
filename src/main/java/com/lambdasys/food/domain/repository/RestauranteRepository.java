package com.lambdasys.food.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lambdasys.food.domain.model.Restaurante;
import com.lambdasys.food.domain.repository.custom.CustomJpaRepository;
import com.lambdasys.food.domain.repository.queries.RestauranteRepositoryQueries;

@Repository
public interface RestauranteRepository 
	extends CustomJpaRepository<Restaurante,Long> , 
			RestauranteRepositoryQueries ,
			JpaSpecificationExecutor<Restaurante>{

	@Query("SELECT r FROM Restaurante r JOIN FETCH r.cozinha JOIN FETCH r.formasPagamento ")
	public List<Restaurante> findAll();
	
}
