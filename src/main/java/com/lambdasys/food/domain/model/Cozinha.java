package com.lambdasys.food.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lambdasys.food.core.validation.Groups;
import com.lambdasys.food.core.validation.Groups.CozinhaId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString

@Entity
@SuppressWarnings("serial")
public class Cozinha implements Serializable {

	@Id
	@NotNull(groups = CozinhaId.class)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@Column(name="id")
	private Long id;
	
	@NotBlank
	@Column(name="nome")
	private String nome;
	
	@NotBlank
	@Column(name="descricao")
	private String descricao;

	@JsonIgnore
	@OneToMany(mappedBy = "cozinha")
	private List<Restaurante> restaurantes = new ArrayList<>();
	
}
