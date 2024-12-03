package br.com.fiap.soat07.clean.infra.repository.mongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class DatabaseConfig {

	@Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://techchallenge:techchallenge@mongodb:27017/local?authSource=admin");
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "local");
    }
}
