package br.com.fiap.soat07.clean.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BusinessExceptionTest {
	
	@Test
	void shouldTestBusinessException() {
		
		assertEquals(new BusinessException("teste").getMessage(), "teste");

	}

}
