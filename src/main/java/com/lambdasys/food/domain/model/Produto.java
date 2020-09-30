package com.lambdasys.food.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@SuppressWarnings("serial")
public class Produto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@NotNull 
	@NotBlank
	@Column(name="nome",nullable = false)
	private String nome;
	
	@NotNull
	@NotBlank
	@Column(name="descricao",nullable = false)
	private String descricao;
	
	@NotNull
	@Column(name="preco", nullable = false)
	private BigDecimal preco;
	
	@NotNull
	@Column(name="ativo" , nullable = false)
	private Boolean ativo = true;
	
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurante restaurante;
	
}
