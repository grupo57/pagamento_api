package br.com.fiap.soat07.clean.infra.rest.dto;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.code.beanmatchers.BeanMatchers;
import com.google.code.beanmatchers.ValueGenerator;

import br.com.fiap.soat07.clean.core.domain.enumeration.TipoProdutoEnum;

public class DTOTest {
	
	@BeforeAll
	static void setup(){
		BeanMatchers.registerValueGenerator(new ValueGenerator<LocalDateTime>() {
			@Override
			public LocalDateTime generate() {
				long startEpochDay = LocalDate.of(1950, 1, 1).toEpochDay();
				long endEpochDay = LocalDate.of(2050, 12, 31).toEpochDay();
				long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);
				return LocalDateTime.ofEpochSecond(randomDay, 1, ZoneOffset.ofHours(1));
			}
		}, LocalDateTime.class);

		BeanMatchers.registerValueGenerator(new ValueGenerator<LocalDate>() {
			@Override
			public LocalDate generate() {
				// Generate a random LocalDate between 1950 and 2050
				long startEpochDay = LocalDate.of(1950, 1, 1).toEpochDay();
				long endEpochDay = LocalDate.of(2050, 12, 31).toEpochDay();
				long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);
				return LocalDate.ofEpochDay(randomDay);
			}
		}, LocalDate.class);

	}
	
	
	@Test
	void shouldTestHasValidGettersAndSetters() {

		
		assertThat(PagamentoDTO.class, hasValidGettersAndSetters());
		assertThat(PedidoDTO.class, hasValidGettersAndSetters());
		assertThat(ProdutoDTO.class, hasValidGettersAndSetters());
		assertThat(ClienteDTO.class, hasValidGettersAndSetters());
		assertThat(ErroTemplateDTO.class, hasValidGettersAndSetters());
	}

	@Test
	void shouldTestHasValidHashCode() {

		assertThat(PagamentoDTO.class, hasValidBeanHashCode());
		assertThat(PedidoDTO.class, hasValidBeanHashCode());
		assertThat(ProdutoDTO.class, hasValidBeanHashCode());
		assertThat(ClienteDTO.class, hasValidBeanHashCode());
		assertThat(ErroTemplateDTO.class, hasValidBeanHashCode());
	}

	@Test
	void shouldTestHasValidBeanEquals() {

		assertThat(PagamentoDTO.class, hasValidBeanEquals());
		assertThat(PedidoDTO.class, hasValidBeanEquals());
		assertThat(ProdutoDTO.class, hasValidBeanEquals());
		assertThat(ClienteDTO.class, hasValidBeanEquals());
		assertThat(ErroTemplateDTO.class, hasValidBeanEquals());
	}

	@Test
	void shouldTestHasValidToString() {

		assertEquals(new PagamentoDTO().toString(), new PagamentoDTO().toString());
		assertEquals(new PedidoDTO().toString(), new PedidoDTO().toString());
		assertEquals(new ProdutoDTO().toString(), new ProdutoDTO().toString());
		assertEquals(new ClienteDTO().toString(), new ClienteDTO().toString());
		
		assertEquals(new ErroTemplateDTO().toString(), new ErroTemplateDTO().toString());

	}
	
	@Test
	void shouldTestOtherMethods() {

		assertEquals(BigDecimal.ZERO, PedidoDTO.builder().build().retrieveValor());
		assertEquals(new BigDecimal(10.20), PedidoDTO.builder().produtos(mockProdutoDTOSet()).build().retrieveValor());		
		assertEquals(BigDecimal.ZERO, ProdutoDTO.builder().build().retrieveValor());
		assertEquals(new BigDecimal(10.20), ProdutoDTO.builder().valor(new BigDecimal(10.20)).build().retrieveValor());
		
	
	}


	private Set<ProdutoDTO> mockProdutoDTOSet() {
		Set<ProdutoDTO> produtos = new HashSet<>();
		produtos.add(mockProdutoDTO());
		return produtos;
	}
	
	private ProdutoDTO mockProdutoDTO() {
		ProdutoDTO dto = new ProdutoDTO();
		dto.setCodigo("COD1");
		dto.setId(1l);
		dto.setNome("test");
		dto.setTipoProduto(TipoProdutoEnum.LANCHE);
		dto.setValor(new BigDecimal(10.20));
		return dto;
	}
	


}
