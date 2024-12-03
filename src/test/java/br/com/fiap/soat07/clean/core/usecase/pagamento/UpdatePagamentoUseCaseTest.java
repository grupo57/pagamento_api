package br.com.fiap.soat07.clean.core.usecase.pagamento;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.core.gateway.PagamentoGateway;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;

@ExtendWith(MockitoExtension.class)
public class UpdatePagamentoUseCaseTest {
	
	@InjectMocks
	UpdatePagamentoUseCase useCase;
	
	@Mock
	private PagamentoGateway pagamentoGateway;
	
	@Mock
	private PedidoGateway pedidoGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new UpdatePagamentoUseCase(pagamentoGateway, pedidoGateway);
	}
	
	@Test
	void shouldTestExecutar() {		

		when(pagamentoGateway.update(any(Pagamento.class))).thenReturn(mockPagamento(PagamentoStatusEnum.SOLICITADO));

		assertEquals(mockPagamento(PagamentoStatusEnum.SOLICITADO), useCase.executar(mockPagamento(PagamentoStatusEnum.SOLICITADO)));
		verify( pagamentoGateway, times(1)).update(any(Pagamento.class));
		verify( pedidoGateway, times(0)).updateStatusPedido(anyLong(), any(PedidoStatusEnum.class));
	}
	
	@Test
	void shouldTestExecutarAndUpdateStatusPedido() {		

		when(pagamentoGateway.update(any(Pagamento.class))).thenReturn(mockPagamento(PagamentoStatusEnum.PAGO));

		assertEquals(mockPagamento(PagamentoStatusEnum.PAGO), useCase.executar(mockPagamento(PagamentoStatusEnum.PAGO)));
		verify( pagamentoGateway, times(1)).update(any(Pagamento.class));
		verify( pedidoGateway, times(1)).updateStatusPedido(anyLong(), any(PedidoStatusEnum.class));
	}
	
	
	
	
	private Pagamento mockPagamento(PagamentoStatusEnum status) {
		Pagamento pagamento = new Pagamento();
		pagamento.setStatus(status);
		pagamento.setPedidoId(1L);
		return pagamento;
	}

}
