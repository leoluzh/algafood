package com.lambdasys.food.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lambdasys.food.core.validation.Groups.CozinhaId;
import com.lambdasys.food.core.validation.Groups.RestauranteId;
import com.lambdasys.food.core.validation.TaxaFrete;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded=true)

@Entity
@SuppressWarnings("serial")
public class Restaurante implements Serializable {

	@Id
	@NotNull(groups = RestauranteId.class)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	@EqualsAndHashCode.Include
	private Long id;
	
	@NotBlank
	@Column(name="nome",nullable = false)
	private String nome;
	
	//@DecimalMin(value ="0")
	//@PositiveOrZero
	//@NotNull
	@TaxaFrete
	@Column(name="taxa_frete",nullable = false)
	private BigDecimal taxaFrete;
	
	@Valid
	@ConvertGroup(from = Default.class , to = CozinhaId.class)
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cozinha_id")
	private Cozinha cozinha;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name="restaurante_forma_pagamento",
		joinColumns = @JoinColumn(name="restaurante_id"),
		inverseJoinColumns = @JoinColumn(name="forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<FormaPagamento>();

	@JsonIgnore
	@Embedded
	private Endereco endereco;
	
	@JsonIgnore
	@CreationTimestamp
	@NotNull
	@Column(name="data_cadastro" , nullable = false ,columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;
	
	@JsonIgnore
	@UpdateTimestamp
	@NotNull
	@Column(name="data_atualizacao" , nullable = false , columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos;
	
}
