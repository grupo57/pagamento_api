package br.com.fiap.soat07.clean.infra.repository.api;

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
import br.com.fiap.soat07.clean.core.gateway.PagamentoMercadoPagoGateway;
import br.com.fiap.soat07.clean.infra.rest.dto.PagamentoDTO;
import br.com.fiap.soat07.clean.infra.rest.mapper.PagamentoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class MercadoPagoRepository implements PagamentoMercadoPagoGateway {
	
	@Value("${application.pagamento.mercadopago-url}")
	private String PAGAMENTO_MERCADOPAGO_URL;
	
	private final RestTemplate restTemplate;
	
	private final PagamentoMapper mapper;

	@Override
	@Retryable(retryFor = HttpClientErrorException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	public void executa(Pagamento request) {
		
		PagamentoDTO pagamentoDTO = mapper.toDTO(request);
		
		
		HttpEntity<PagamentoDTO> requestEntity = new HttpEntity<>(pagamentoDTO);
		
		try {
		ResponseEntity<PagamentoDTO> response =
				restTemplate.exchange(PAGAMENTO_MERCADOPAGO_URL, HttpMethod.POST, requestEntity, PagamentoDTO.class);
		log.info("Pagamento realizado com sucesso qrcode: {}", response.getBody().getQrcode());

		}catch(RuntimeException e) {
			log.error("Não foi possível realizar o pagamento: {}", request.getId(), e);
			throw new RuntimeException(String.format("Falha na comunicação: não foi possível realizar o pagamento: %s", request.getId()), e);
		}

	}
	
	

}




