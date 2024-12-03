package br.com.fiap.soat07.clean.core.usecase.pagamento;

import java.util.Collection;
import java.util.Optional;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.exception.PagamentoNotFoundException;
import br.com.fiap.soat07.clean.core.gateway.PagamentoGateway;

public class SearchPagamentoUseCase {

	private final PagamentoGateway pagamentoGateway;


	public SearchPagamentoUseCase(PagamentoGateway pagamentoGateway) {
		this.pagamentoGateway = pagamentoGateway;
	}


	/**
	 * Get Pagamento by pedidoId
	 * @param id {@link Long}
	 * @return {@link Optional<Pagamento>}
	 */
	public Optional<Pagamento> findById(String id) {
		if (id == null)
			throw new IllegalArgumentException("Obrigatório informar código do pagamento");

		Pagamento pagamento = pagamentoGateway.findById(id).orElseThrow(() -> new PagamentoNotFoundException(id));

		return Optional.of(pagamento);
	}

	/**
	 * Get All
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Collection<Pagamento> find(int pageNumber, int pageSize) {
		return pagamentoGateway.find(pageNumber, pageSize);
	}



}
