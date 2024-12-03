package br.com.fiap.soat07.clean.core.gateway;

import java.util.Collection;
import java.util.Optional;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;

public interface PagamentoGateway {

	PagamentoStatusEnum getSituacao(Pagamento pagamento);

	Pagamento create(Pagamento pagamento);
	
	Pagamento update(Pagamento pagamento);
	
	Collection<Pagamento> find(int pageNUmber, int pageSize);
	
	Optional<Pagamento> findById(String id);

	String getQRCode(Pagamento pagamento);

}
