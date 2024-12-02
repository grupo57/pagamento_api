package br.com.fiap.soat07.clean.core.usecase.pagamento;

import java.util.Locale;
import java.util.UUID;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.gateway.PagamentoGateway;
import br.com.fiap.soat07.clean.core.gateway.PagamentoMercadoPagoGateway;

public class CreatePagamentoUseCase {

	private final PagamentoGateway pagamentoGateway;
	
	private final PagamentoMercadoPagoGateway pagamentoMercadoPagoGateway;

	public CreatePagamentoUseCase(PagamentoGateway pagamentoGateway, PagamentoMercadoPagoGateway pagamentoMercadoPagoGateway) {
		this.pagamentoGateway = pagamentoGateway;
		this.pagamentoMercadoPagoGateway = pagamentoMercadoPagoGateway;
	}

	public Pagamento executar(Pagamento pagamento) {
		
		pagamento.setStatus(PagamentoStatusEnum.SOLICITADO);
		pagamento.setQrcode(gerarQrcode());
		Pagamento pagamentoCriado = pagamentoGateway.create(pagamento);	
		
		pagamentoMercadoPagoGateway.executa(pagamentoCriado);
		
		return pagamentoCriado;
	}

	
	private String gerarQrcode() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase(Locale.US);
	}
}
