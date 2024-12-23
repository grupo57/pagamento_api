package br.com.fiap.soat07.clean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.retry.annotation.EnableRetry;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@EnableRetry
@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class})
@OpenAPIDefinition(info = @Info(title = "Lanchonete Self Service Pagamento API", version = "2.0", description = "Sistema de pagamento Self Service para Lanchonete"))
public class TechChallenge04PagamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechChallenge04PagamentoApplication.class, args);
	}

}
