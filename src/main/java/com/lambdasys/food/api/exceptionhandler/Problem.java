package com.lambdasys.food.api.exceptionhandler;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * RFC - 
 * @author leoluzh
 *
 */

@JsonInclude(Include.NON_NULL)
@Data
@Builder
@SuppressWarnings("serial")
public class Problem implements Serializable {

	/**
	 * status - codigo status http da resposta
	 */
	private Integer status;
	/**
	 * uri - especifica tipo do problema
	 */
	private String type;
	/**
	 * descricao do tipo do problema
	 */
	private String title;
	/**
	 * descricao detalhada do problema
	 */
	private String detail;
	
	@Builder.Default
	private OffsetDateTime timestamp = OffsetDateTime.now();
	
	private String userMessage;
	
	private List<Field> fields;
	
	
}
