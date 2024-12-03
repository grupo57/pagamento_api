package br.com.fiap.soat07.clean.core.usecase.pagamento;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.gateway.PagamentoGateway;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;

public class UpdatePagamentoUseCase {

	private final PagamentoGateway pagamentoGateway;
	private final PedidoGateway pedidoGateway;

	public UpdatePagamentoUseCase(PagamentoGateway pagamentoGateway, PedidoGateway pedidoGateway) {
		this.pagamentoGateway = pagamentoGateway;
		this.pedidoGateway = pedidoGateway;
	}

	public Pagamento executar(Pagamento pagamento) {
		
		Pagamento pagamentoAtualizado = pagamentoGateway.update(pagamento);
		
		if(pagamento.getStatus() == PagamentoStatusEnum.PAGO) {
			pedidoGateway.updateStatusPedido(pagamento.getPedidoId(), PedidoStatusEnum.PAGO);
		}
		
		return pagamentoAtualizado;

	}

}
