package com.lambdasys.food.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pedido")
@SuppressWarnings("serial")
public class Pedido implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="subtotal")
	private BigDecimal subtotal;
	
	@Column(name="taxaFrete")
	private BigDecimal taxaFrete;
	
	@Column(name="valorTotal")
	private BigDecimal valorTotal;
	
	@Column(name="dataCriacao")
	private OffsetDateTime dataCriacao;
	
	@Column(name="dataConfirmacao")
	private OffsetDateTime dataConfirmacao;
	
	@Column(name="dataCancelamento")
	private OffsetDateTime dataCancelamento;
	
	@Column(name="dataEntrega")
	private OffsetDateTime dataEntrega;
	
	
}
