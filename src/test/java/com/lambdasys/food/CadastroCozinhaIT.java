package com.lambdasys.food;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lambdasys.food.domain.exceptions.CozinhaNaoEncontradaException;
import com.lambdasys.food.domain.exceptions.EntidadeEmUsoException;
import com.lambdasys.food.domain.model.Cozinha;
import com.lambdasys.food.domain.service.CozinhaService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CadastroCozinhaIT {

	@Autowired
	private CozinhaService cozinhaService;
	
	@Test
	@DisplayName("Deve atribuuir id para cozinha quando cadastrar com dados corretos.")
	public void deve() {
		//cenario
		Cozinha nova = new Cozinha();
		nova.setNome("Chinesa");
		//acao
		nova = cozinhaService.save(nova);
		//validacao
		assertThat(nova).isNotNull();
		assertThat(nova.getId()).isNotNull();
	}
	
	@Test
	@DisplayName("Deve falhar quando cadastrar cozinha sem nome.")
	public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
		//cenario
		final Cozinha nova = new Cozinha();
		nova.setNome(null);
		//acao
		assertThrows( ConstraintViolationException.class , () -> cozinhaService.save( nova ) );
		//validacao
		
	}
	
	@Test
	@DisplayName("Deve falhar quando excluir cozinha um uso.")
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		assertThrows( EntidadeEmUsoException.class , () -> cozinhaService.delete(2L) );
	}
	
	@Test
	@DisplayName("Deve falhar quando excluir uma cozinha inexistente.")
	public void deveFalhar_QuandoExcluirUmaCozinhaInexistente() {
		assertThrows( CozinhaNaoEncontradaException.class , () -> cozinhaService.delete(9999999L) );		
	}
	
	
}
