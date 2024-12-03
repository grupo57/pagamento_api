package br.com.fiap.soat07.clean.infra.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.fiap.soat07.clean.core.gateway.PagamentoGateway;
import br.com.fiap.soat07.clean.core.gateway.PagamentoMercadoPagoGateway;
import br.com.fiap.soat07.clean.core.gateway.PedidoGateway;
import br.com.fiap.soat07.clean.core.usecase.pagamento.CreatePagamentoUseCase;
import br.com.fiap.soat07.clean.core.usecase.pagamento.SearchPagamentoUseCase;
import br.com.fiap.soat07.clean.core.usecase.pagamento.UpdatePagamentoUseCase;

@ExtendWith(MockitoExtension.class)
public class PagamentoServiceTest {
	
	@InjectMocks
	PagamentoService pagamentoService;
	 
	@Mock
	private CreatePagamentoUseCase createPagamentoUseCase;
	
	@Mock
    private SearchPagamentoUseCase searchPagamentoUseCase;
	
	@Mock
    private UpdatePagamentoUseCase updatePagamentoUseCase;
	
	@Mock
    private PagamentoGateway pagamentoGateway;
	
	@Mock
    private PagamentoMercadoPagoGateway pagamentoMercadoPagoGateway;
	
	@Mock
    private PedidoGateway pedidoGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		pagamentoService = new PagamentoService(pagamentoGateway, pagamentoMercadoPagoGateway, pedidoGateway);
		ReflectionTestUtils.setField(pagamentoService, "createPagamentoUseCase", createPagamentoUseCase);
		ReflectionTestUtils.setField(pagamentoService, "searchPagamentoUseCase", searchPagamentoUseCase);
		ReflectionTestUtils.setField(pagamentoService, "updatePagamentoUseCase", updatePagamentoUseCase);
	}
	
	@Test
	void shouldTestGetCreatePagamentoUseCase() {		
				
		assertNotNull(pagamentoService.getCreatePagamentoUseCase());
	}

	@Test
	void shouldTestGetSearchPagamentoUseCase() {		
				
		assertNotNull(pagamentoService.getSearchPagamentoUseCase());
	}

	@Test
	void shouldTestGetUpdatePagamentoUseCase() {		
				
		assertNotNull(pagamentoService.getUpdatePagamentoUseCase());
	}
	

}
