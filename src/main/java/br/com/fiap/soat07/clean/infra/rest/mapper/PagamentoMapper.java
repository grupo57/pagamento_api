package br.com.fiap.soat07.clean.infra.rest.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.infra.repository.mongo.model.PagamentoModel;
import br.com.fiap.soat07.clean.infra.rest.dto.CreatePagamentoDTO;
import br.com.fiap.soat07.clean.infra.rest.dto.PagamentoDTO;

@Mapper (componentModel = "spring")
public interface PagamentoMapper {
	
	static PagamentoMapper INSTANCE = Mappers.getMapper(PagamentoMapper.class);
	
	/**
	 * To domain mapper
	 * @param pagamentoDTO PagamentoDTO
	 * @return {@link Pagamento}
	 */
	Pagamento toDomain(PagamentoDTO pagamentoDTO);
	
	/**
	 * To domain mapper
	 * @param pagamentoDTO PagamentoDTO
	 * @return {@link Pagamento}
	 */
	Pagamento toDomain(CreatePagamentoDTO pagamentoDTO);
	
	/**
	 * To domain mapper
	 * @param pagamentoModel PagamentoModel
	 * @return {@link Pagamento}
	 */
	Pagamento toDomain(PagamentoModel pagamentoModel);
	
	/**
	 * To domain mapper
	 * @param pagamentos List<PagamentoModel>
	 * @return {@link List<Pagamento>}
	 */
	List<Pagamento> toDomain(List<PagamentoModel> pagamentos);
	
	/**
	 * To dto mapper
	 * @param pagamento Pagamento
	 * @return {@link PagamentoDTO}
	 */
	PagamentoDTO toDTO(Pagamento pagamento);

	/**
	 * To model mapper
	 * @param pagamento Pagamento
	 * @return {@link PagamentoModel}
	 */
	PagamentoModel toModel(Pagamento pagamento);

}
