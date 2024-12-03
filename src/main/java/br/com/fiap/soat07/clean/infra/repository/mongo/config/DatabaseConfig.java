package br.com.fiap.soat07.clean.infra.repository.mongo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class DatabaseConfig {
	
	@Value("${spring.data.mongodb.username}")
	private String username;
	
	@Value("${spring.data.mongodb.password}")
	
	private String password;

	@Bean
    public MongoClient mongoClient() {
        return MongoClients.create(String.format("mongodb://%s:%s@mongodb:27017/local?authSource=admin",username, password));
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "local");
    }
}
