package br.com.fiap.soat07.clean.core.usecase.pagamento;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
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
	void shouldTestExecutarStatusPago() {		

		//when(pedidoGateway.save(any(Pedido.class))).thenReturn(mockPedido());
		//when(pedidoGateway.save(any(Pedido.class), any(Pagamento.class))).thenReturn(mockPagamento());

		//assertNotNull(useCase.executar(mockPedido(), mockPagamento(), PagamentoStatusEnum.PAGO));
		//verify( pedidoGateway, times(1)).save(any(Pedido.class));
		//verify( pedidoGateway, times(1)).save(any(Pedido.class), any(Pagamento.class));
	}
	
	@Test
	void shouldTestExecutarOutroStatus() {		

		//when(pedidoGateway.save(any(Pedido.class))).thenReturn(mockPedido());
		//when(pedidoGateway.save(any(Pedido.class), any(Pagamento.class))).thenReturn(mockPagamento());

		//assertNotNull(useCase.executar(mockPedido(), mockPagamento(), PagamentoStatusEnum.RECUSADO));
		//verify( pedidoGateway, times(1)).save(any(Pedido.class));
		//verify( pedidoGateway, times(1)).save(any(Pedido.class), any(Pagamento.class));
	}
	
	
	
	private Pagamento mockPagamento() {
		Pagamento pagamento = new Pagamento();
		return pagamento;
	}

}
