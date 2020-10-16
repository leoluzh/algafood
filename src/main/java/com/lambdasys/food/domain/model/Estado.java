package com.lambdasys.food.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.lambdasys.food.core.validation.Groups.EstadoId;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@Table(name="estado")
@SuppressWarnings("serial")
public class Estado implements Serializable{

	@Id
	@NotNull(groups = EstadoId.class)
	@Column(name="id")
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(name="nome")
	private String nome;

	@NotBlank
	@Column(name="sigla")
	private String sigla;
	
	@NotBlank
	@Column(name="codigo_ibge")
	private Integer codigoIbge;
	
}
