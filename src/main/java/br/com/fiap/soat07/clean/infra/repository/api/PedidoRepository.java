package br.com.fiap.soat07.clean.infra.repository.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;
import br.com.fiap.soat07.clean.infra.rest.dto.PedidoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class PedidoRepository implements PedidoGateway {
	
	@Value("${application.pedido.update-status-url}")
	private String UPDATE_STATUS_PEDIDO_URL;
	
	private final RestTemplate restTemplate;
	
	

	@Override
	public void updateStatusPedido(Long pedidoId, PedidoStatusEnum status) {
		
		HttpEntity<String> requestEntity = new HttpEntity<>("");

		String  url = UriComponentsBuilder.fromUriString(UPDATE_STATUS_PEDIDO_URL)
				.pathSegment(String.format("%s", pedidoId))
	            .queryParam("status", status)
	            .build()
	            .toUriString();
		
		try {
		ResponseEntity<PedidoDTO> response =
				restTemplate.exchange(url, HttpMethod.PATCH, requestEntity, PedidoDTO.class);
		log.info("Status do pedido atualizado com sucesso. \n Resposta: {}", new Gson().toJson(response));

		}catch(RuntimeException e) {
			log.error("Não foi possível atualizar o status do pedido: {} para {}", pedidoId, status, e);
			throw new RuntimeException(String.format("Falha na comunicação: não foi possível atualizar o status do pedido: %s para %s", pedidoId, status), e);
		}

	}
	
	

}




