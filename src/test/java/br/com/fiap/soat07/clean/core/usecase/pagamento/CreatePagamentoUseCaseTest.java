package br.com.fiap.soat07.clean.core.usecase.pagamento;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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

		when(pagamentoGateway.create(any(Pagamento.class))).thenReturn(mockPagamento());
		
		doNothing().when(pagamentoMercadoPagoGateway).executa(any(Pagamento.class));

		assertNotNull(useCase.executar(mockPagamento()));
		verify( pagamentoGateway, times(1)).create(any(Pagamento.class));
		verify( pagamentoMercadoPagoGateway, times(1)).executa(any(Pagamento.class));
	}
	
	private Pagamento mockPagamento() {
		Pagamento pagamento = new Pagamento();
		return pagamento;
	}

	
	
}
