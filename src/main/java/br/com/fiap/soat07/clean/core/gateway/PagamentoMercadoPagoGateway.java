package br.com.fiap.soat07.clean.core.gateway;

import org.springframework.scheduling.annotation.Async;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;

public interface PagamentoMercadoPagoGateway {
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@Async
	void executa(Pagamento request);

}
