package br.com.fiap.soat07.clean.infra.repository.mongo.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import br.com.fiap.soat07.clean.infra.repository.mongo.config.AuditingConfig;

public class AuditingConfigTest {
	
	@Test
	void shouldTestCreateProduto() {		

		AuditingConfig config = new AuditingConfig();
				
		assertNotNull(config.dateTimeProvider());

	}

}
