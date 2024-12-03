package br.com.fiap.soat07.clean.infra.repository.mongo.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DatabaseConfigTest {
	
	@InjectMocks
	DatabaseConfig config = new DatabaseConfig();
	

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void shouldTestBeanMongoClient() {					
		assertNotNull(config.mongoClient());
	}
	
	@Test
	void shouldTestBeanMongoTemplate() {					
		assertNotNull(config.mongoTemplate(config.mongoClient()));
	}

}
