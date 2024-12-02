package br.com.fiap.soat07.clean.core.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.fiap.soat07.clean.core.domain.enumeration.MetodoPagamentoEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.ProvedorPagamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pagamento {
	
	private String id;
	private Long pedidoId;
	private PagamentoStatusEnum status = PagamentoStatusEnum.NAO_CONCLUIDO;
	private ProvedorPagamentoEnum provedorServico;
	private MetodoPagamentoEnum metodoPagamento;
	private String qrcode;
	private LocalDateTime data;
	private BigDecimal valor;

}
