package br.com.fiap.soat07.clean.core.usecase.pagamento;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.exception.PagamentoNotFoundException;
import br.com.fiap.soat07.clean.core.gateway.PagamentoGateway;

@ExtendWith(MockitoExtension.class)
public class SearchPagamentoUseCaseTest {

	@InjectMocks
	SearchPagamentoUseCase useCase;
	 
	@Mock
	PagamentoGateway pagamentoGateway;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		useCase = new SearchPagamentoUseCase(pagamentoGateway);
	}
	
	@Test
	void shouldTestFindById() {		

		when(pagamentoGateway.findById(anyString())).thenReturn(Optional.of(mockPagamento()));

		assertNotNull(useCase.findById("COD_PAGAMENTO"));
		verify( pagamentoGateway, times(1)).findById(anyString());
	}
	
	@Test
	void shouldTestFindByIdPedidoNotFoundException() {		

		when(pagamentoGateway.findById(anyString())).thenReturn(Optional.empty());
		
		assertThatThrownBy(() -> useCase.findById("COD_PAGAMENTO"))
        .isInstanceOf(PagamentoNotFoundException.class)
        .hasMessage("Não foi encontrado um pagamento com o Id:COD_PAGAMENTO");

	}
	
	@Test
	void shouldTestFindByIdIllegalArgumentException() {		
		
		assertThatThrownBy(() -> useCase.findById(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Obrigatório informar código do pagamento");

	}
	
	@Test
	void shouldTestFind() {		

		when(pagamentoGateway.find(anyInt(), anyInt())).thenReturn(mockPagamentos());

		assertNotNull(useCase.find(1, 10));
		verify(pagamentoGateway, times(1)).find(anyInt(), anyInt());
	}
	
		
	
	private Collection<Pagamento> mockPagamentos() {
		List<Pagamento> pagamentos = new ArrayList<>();
		pagamentos.add(mockPagamento());
		return pagamentos;
	}

	private Pagamento mockPagamento() {
		Pagamento pagamento = new Pagamento();
		return pagamento;
	}
		
}
