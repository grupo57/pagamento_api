package br.com.fiap.soat07.clean.infra.repository.api;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.infra.rest.dto.PagamentoDTO;
import br.com.fiap.soat07.clean.infra.rest.mapper.PagamentoMapper;


@ExtendWith(MockitoExtension.class)
public class MercadoPagoRepositoryTest {
	
	
	@Mock
	private RestTemplate restTemplate;
	
	private PagamentoMapper mapper = PagamentoMapper.INSTANCE;
	
	@InjectMocks
	private MercadoPagoRepository repository;
	
	private String PAGAMENTO_MERCADOPAGO_URL = "http://localhost";
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		repository = new MercadoPagoRepository(restTemplate, mapper);
		ReflectionTestUtils.setField(repository, "PAGAMENTO_MERCADOPAGO_URL", PAGAMENTO_MERCADOPAGO_URL);
		ReflectionTestUtils.setField(repository, "mapper", mapper);
	}
	
	@Test
	void shouldTestSendSuccess() {
		
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), eq(PagamentoDTO.class))).thenReturn(mockResponseEntityPagamentoDTO());

		repository.executa(mockPagamento());
		verify(restTemplate, times(1)).exchange(anyString(), any(HttpMethod.class), any(), eq(PagamentoDTO.class));
		
	}
	
	@Test
	void shouldTestSendRuntimeException() {
		
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), eq(PagamentoDTO.class))).thenThrow(RuntimeException.class);
		
		assertThatThrownBy(() -> repository.executa(mockPagamento()))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("Falha na comunicação: não foi possível realizar o pagamento: ABC123");
				
		verify(restTemplate, times(1)).exchange(anyString(), any(HttpMethod.class), any(), eq(PagamentoDTO.class));
		
	}
	

	private ResponseEntity<PagamentoDTO> mockResponseEntityPagamentoDTO() {
		return ResponseEntity.ok(mockPagamentoDTO());
	}

	private PagamentoDTO mockPagamentoDTO() {
		return PagamentoDTO.builder()
		.id("ABC123")
		.build();
	}
	
	private Pagamento mockPagamento() {
		Pagamento pagamento = Pagamento.builder()
		.id("ABC123")
		.build();
		return pagamento;
	}

}


