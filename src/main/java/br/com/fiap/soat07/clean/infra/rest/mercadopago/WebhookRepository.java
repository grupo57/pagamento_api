package br.com.fiap.soat07.clean.infra.rest.mercadopago;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.infra.rest.dto.PagamentoDTO;
import br.com.fiap.soat07.clean.infra.rest.mapper.PagamentoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebhookRepository implements WebhookGateway {
	
	@Value("${application.pagamento.webhook-url}")
	private String WEBHOOK_URL;
	
	private final RestTemplate restTemplate;
	
	private final PagamentoMapper mapper;

	@Override
	@Retryable(retryFor = HttpClientErrorException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	public void sendResponse(Pagamento request) {
		
		PagamentoDTO pagamentoDTO = mapper.toDTO(request);
		
		
		HttpEntity<PagamentoDTO> requestEntity = new HttpEntity<>(pagamentoDTO);
		
		try {
		Thread.sleep(10000L); //espera 10s
		ResponseEntity<PagamentoDTO> response =
				restTemplate.exchange(WEBHOOK_URL, HttpMethod.POST, requestEntity, PagamentoDTO.class);
		log.info("Pedido enviado com sucesso para a cozinha. \n Resposta: {}", response.getBody().toString());

		}catch(InterruptedException | RuntimeException e) {
			log.error("Não foi possível retornar o status do pagamento: {}", request.getId(), e);
			throw new RuntimeException(String.format("Falha na comunicação: não foi possível retornar o status do pagamento: {}", request.getId()), e);
		}

	}
	
	

}




