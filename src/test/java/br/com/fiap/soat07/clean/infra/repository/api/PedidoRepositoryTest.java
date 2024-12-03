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

import br.com.fiap.soat07.clean.core.domain.enumeration.PedidoStatusEnum;
import br.com.fiap.soat07.clean.infra.rest.dto.PedidoDTO;


@ExtendWith(MockitoExtension.class)
public class PedidoRepositoryTest {
	
	
	@Mock
	private RestTemplate restTemplate;
	
	@InjectMocks
	private PedidoRepository repository;
	
	private String UPDATE_STATUS_PEDIDO_URL = "http://localhost";
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		repository = new PedidoRepository(restTemplate);
		ReflectionTestUtils.setField(repository, "UPDATE_STATUS_PEDIDO_URL", UPDATE_STATUS_PEDIDO_URL);
	}
	
	@Test
	void shouldTestSendSuccess() {
		
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), eq(PedidoDTO.class))).thenReturn(mockResponseEntityPedidoDTO());

		repository.updateStatusPedido(1L, PedidoStatusEnum.PAGO);
		verify(restTemplate, times(1)).exchange(anyString(), any(HttpMethod.class), any(), eq(PedidoDTO.class));
		
	}
	
	@Test
	void shouldTestSendRuntimeException() {
		
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), eq(PedidoDTO.class))).thenThrow(RuntimeException.class);
		
		assertThatThrownBy(() -> repository.updateStatusPedido(1L, PedidoStatusEnum.PAGO))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("Falha na comunicação: não foi possível atualizar o status do pedido: 1 para PAGO");
				
		verify(restTemplate, times(1)).exchange(anyString(), any(HttpMethod.class), any(), eq(PedidoDTO.class));
		
	}
	

	private ResponseEntity<PedidoDTO> mockResponseEntityPedidoDTO() {
		return ResponseEntity.ok(mockPedidoDTO());
	}

	private PedidoDTO mockPedidoDTO() {
		return PedidoDTO.builder()
		.id(1L)
		.build();
	}
	
}



