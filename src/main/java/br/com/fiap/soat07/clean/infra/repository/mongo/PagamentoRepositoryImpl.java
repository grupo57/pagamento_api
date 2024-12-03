package br.com.fiap.soat07.clean.infra.repository.mongo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.gateway.PagamentoGateway;
import br.com.fiap.soat07.clean.infra.repository.mongo.model.PagamentoModel;
import br.com.fiap.soat07.clean.infra.rest.mapper.PagamentoMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class PagamentoRepositoryImpl implements  PagamentoGateway{
	
    private final MongoTemplate mongoTemplate;
    
    final private PagamentoMapper mapper;

	@Override
	public PagamentoStatusEnum getSituacao(Pagamento pagamento) {		
		return mongoTemplate.findById(pagamento.getId(), Pagamento.class).getStatus();
	}

	@Override
	public Pagamento create(Pagamento pagamento) {
		PagamentoModel pagamentoModel = mapper.toModel(pagamento);
		PagamentoModel createdPagamentoModel = mongoTemplate.save(pagamentoModel);
		return mapper.toDomain(createdPagamentoModel);

	}

	@Override
	public Pagamento update(Pagamento pagamento) {
		PagamentoModel pagamentoModel = mapper.toModel(pagamento);
		PagamentoModel updatedPagamentoModel = mongoTemplate.save(pagamentoModel);
		return  mapper.toDomain(updatedPagamentoModel);
	}

	@Override
	public Collection<Pagamento> find(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		List<PagamentoModel> pagamentos = mongoTemplate.find(new Query().with(pageable), PagamentoModel.class);
		
		return mapper.toDomain(pagamentos);
	}

	@Override
	public Optional<Pagamento> findById(String id) {
		PagamentoModel pagamento = mongoTemplate.findById(id, PagamentoModel.class);
		return  Optional.ofNullable( mapper.toDomain(pagamento));  
	}

	@Override
	public String getQRCode(Pagamento pagamento) {
		return mongoTemplate.findById(pagamento.getId(), Pagamento.class).getQrcode();
	}

}
