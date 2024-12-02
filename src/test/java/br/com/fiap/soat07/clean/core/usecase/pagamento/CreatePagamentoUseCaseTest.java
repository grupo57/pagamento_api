package br.com.fiap.soat07.clean.core.usecase.pagamento;

import static org.mockito.ArgumentMatchers.any;
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
import br.com.fiap.soat07.clean.core.gateway.PagamentoGateway;
import br.com.fiap.soat07.clean.core.gateway.PagamentoMercadoPagoGateway;

@ExtendWith(MockitoExtension.class)
public class CreatePagamentoUseCaseTest {
	
	@InjectMocks
	CreatePagamentoUseCase useCase;
	
	@Mock
	PagamentoGateway pagamentoGateway;
	
	@Mock
	private PagamentoMercadoPagoGateway pagamentoMercadoPagoGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new CreatePagamentoUseCase(pagamentoGateway, pagamentoMercadoPagoGateway);
	}
	
	@Test
	void shouldTestExecutarPagamento() {		

		when(pagamentoGateway.getSituacao(any(Pagamento.class))).thenReturn(PagamentoStatusEnum.PAGO);

		//assertNotNull(useCase.executar(mockPedido(), ProvedorPagamentoEnum.MERCADO_PAGO, MetodoPagamentoEnum.CARTAO_CREDITO));
		//verify( pedidoGateway, times(1)).save(any(Pedido.class), any(Pagamento.class));
	}
	
	@Test
	void shouldTestExecutarPagamentoPedidoNotFound() {		


		//when(pagamentoGateway.create(any(Pedido.class))).thenReturn(mockPagamento());
		
		//assertNotNull(useCase.executar(mockPedido(), ProvedorPagamentoEnum.MERCADO_PAGO, MetodoPagamentoEnum.CARTAO_CREDITO));
		//verify( pedidoGateway, times(1)).save(any(Pedido.class), any(Pagamento.class));
	}
	
	private Pagamento mockPagamento() {
		Pagamento pagamento = new Pagamento();
		return pagamento;
	}

	
	
}
