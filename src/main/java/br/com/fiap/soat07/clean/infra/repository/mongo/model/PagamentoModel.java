package br.com.fiap.soat07.clean.infra.repository.mongo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.fiap.soat07.clean.core.domain.enumeration.MetodoPagamentoEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.ProvedorPagamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "pagamentos")
public class PagamentoModel {
	
	@Id
	private String id;
	private Long pedidoId;
	private String qrcode;
	private BigDecimal valor;
	private PagamentoStatusEnum status;
	private ProvedorPagamentoEnum provedorServico;
	private MetodoPagamentoEnum metodoPagamento;
	private LocalDateTime data;
	
}
