package br.com.fiap.soat07.clean.infra.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.fiap.soat07.clean.core.domain.enumeration.MetodoPagamentoEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.ProvedorPagamentoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagamentoDTO {
	
	private String id;	
	private Long pedidoId;	
	private String qrcode;	
	private PagamentoStatusEnum status;
	private BigDecimal valor;
	private LocalDateTime data;
	
	@Builder.Default
	private ProvedorPagamentoEnum provedorServico = ProvedorPagamentoEnum.MERCADO_PAGO;
	
	@Builder.Default
	private MetodoPagamentoEnum metodoPagamento = MetodoPagamentoEnum.QRCODE;
	
	
}
