package br.com.fiap.soat07.clean.infra.rest.mercadopago;

import org.springframework.scheduling.annotation.Async;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;

public interface WebhookGateway {
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@Async
	void sendResponse(Pagamento request);

}
