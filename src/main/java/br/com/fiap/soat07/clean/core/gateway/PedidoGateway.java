package br.com.fiap.soat07.clean.core.gateway;

import org.springframework.scheduling.annotation.Async;

import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;

public interface PedidoGateway {
	
	@Async
	void updateStatusPedido(Long id, PedidoStatusEnum status);

}
