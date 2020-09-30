package com.lambdasys.food.domain.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
@SuppressWarnings("serial")
public class Usuario implements Serializable {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@NotNull
	@NotBlank
	@Column(name="username",nullable = false)
	private String username;
	
	@NotNull
	@NotBlank
	@Column(name="nome",nullable = false)
	private String nome;
	
	@NotNull
	@NotBlank
	@Column(name="email",nullable = false)
	private String email;
	
	@NotNull
	@CreationTimestamp
	@Column(name="data_cadastro",nullable = false)
	private OffsetDateTime dataCadastro;
	
	@NotNull
	@UpdateTimestamp
	@Column(name="data_atualizacao",nullable = false)
	private OffsetDateTime dataAtualizacao;
	
	@ManyToMany
	@JoinTable(name="usuario_grupo", 
		joinColumns = @JoinColumn(name="usuario_id") , 
		inverseJoinColumns = @JoinColumn(name="grupo_id"))
	private List<Grupo> grupos = new ArrayList<Grupo>();
	
}
