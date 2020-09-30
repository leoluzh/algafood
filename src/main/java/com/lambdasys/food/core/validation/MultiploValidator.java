package com.lambdasys.food.core.validation;

import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultiploValidator implements ConstraintValidator<Multiplo,Number>  {

	private int numeroMultiplo;
	
	@Override
	public void initialize(Multiplo constraintAnnotation) {
		this.numeroMultiplo = constraintAnnotation.numero();
	}
	
	
	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		boolean valido = true;
		
		if( Objects.nonNull(value)) {
			var vd = BigDecimal.valueOf(value.doubleValue());
			var md = BigDecimal.valueOf(this.numeroMultiplo);
			var resto = vd.remainder(md);
			valido = BigDecimal.ZERO.compareTo(resto) == 0 ;
		}
		
		return valido;
	}

}
