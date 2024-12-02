package br.com.fiap.soat07.clean.infra.service;

import org.springframework.stereotype.Component;

import br.com.fiap.soat07.clean.core.gateway.PagamentoGateway;
import br.com.fiap.soat07.clean.core.gateway.PagamentoMercadoPagoGateway;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;
import br.com.fiap.soat07.clean.core.usecase.pagamento.CreatePagamentoUseCase;
import br.com.fiap.soat07.clean.core.usecase.pagamento.SearchPagamentoUseCase;
import br.com.fiap.soat07.clean.core.usecase.pagamento.UpdatePagamentoUseCase;

@Component
public class PagamentoService {

    private final CreatePagamentoUseCase createPagamentoUseCase;
    private final SearchPagamentoUseCase searchPagamentoUseCase;
    private final UpdatePagamentoUseCase updatePagamentoUseCase;

    public PagamentoService(final PagamentoGateway pagamentoGateway, final PagamentoMercadoPagoGateway pagamentoMercadoPagoGateway, final PedidoGateway pedidoGateway) {
        this.createPagamentoUseCase = new CreatePagamentoUseCase(pagamentoGateway, pagamentoMercadoPagoGateway);
        this.searchPagamentoUseCase = new SearchPagamentoUseCase(pagamentoGateway);
        this.updatePagamentoUseCase = new UpdatePagamentoUseCase(pagamentoGateway, pedidoGateway);
    }

    public CreatePagamentoUseCase getCreatePagamentoUseCase() {
        return createPagamentoUseCase;
    }

    public SearchPagamentoUseCase getSearchPagamentoUseCase() {
        return searchPagamentoUseCase;
    }

    public UpdatePagamentoUseCase getUpdatePagamentoUseCase() {
        return updatePagamentoUseCase;
    }
}
