package com.lambdasys.food.core.validation;

import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@SuppressWarnings("serial")
public class ValidacaoException extends RuntimeException {

	protected BindingResult bindingResult;
	
}
