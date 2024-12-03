package br.com.fiap.soat07.clean.infra.repository.mongo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.infra.repository.mongo.model.PagamentoModel;
import br.com.fiap.soat07.clean.infra.rest.mapper.PagamentoMapper;

@ExtendWith(MockitoExtension.class)
public class PagamentoRepositoryImplTest {
	
	@InjectMocks
	PagamentoRepositoryImpl repository;
	 
	@Mock
	private MongoTemplate mongoTemplate;
    
    private PagamentoMapper mapper = PagamentoMapper.INSTANCE;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		repository = new PagamentoRepositoryImpl(mongoTemplate, mapper);
	}
	
	@Test
	void shouldTestGetSituacao() {		

		when(mongoTemplate.findById(anyString(), eq(Pagamento.class))).thenReturn(mockPagamento());
				
		assertEquals(PagamentoStatusEnum.PAGO, repository.getSituacao(mockPagamento()));
	}
	
	@Test
	void shouldTestCreate() {		

		when(mongoTemplate.save(any(PagamentoModel.class))).thenReturn(mockPagamentoModel());

		assertEquals(mockPagamento().getId(), repository.create(mockPagamento()).getId());
	}
	
	@Test
	void shouldTestUpdate() {		

		when(mongoTemplate.save(any(PagamentoModel.class))).thenReturn(mockPagamentoModel());

		assertEquals(mockPagamento().getId(), repository.update(mockPagamento()).getId());
	}
	
	@Test
	void shouldTestFind() {		

		when(mongoTemplate.find(any(Query.class),eq(PagamentoModel.class))).thenReturn(mockPagamentos());

		assertEquals(mockPagamentos().size(), repository.find(1, 10).size());
	}
	
	@Test
	void shouldTestFindById() {		

		when(mongoTemplate.findById(anyString(),eq(PagamentoModel.class))).thenReturn(mockPagamentoModel());

		assertEquals(Optional.ofNullable(mockPagamento()).get().getId(), repository.findById("ID").get().getId());
	}
	
	@Test
	void shouldTestGetQRCode() {		

		when(mongoTemplate.findById(anyString(),eq(Pagamento.class))).thenReturn(mockPagamento());

		assertEquals(mockPagamento().getQrcode(), repository.getQRCode(mockPagamento()));
	}
	
	private List<PagamentoModel> mockPagamentos() {
		 List<PagamentoModel> pagamentos = new ArrayList<>();
		 pagamentos.add(mockPagamentoModel());
		return pagamentos;
	}

	private Pagamento mockPagamento() {
		Pagamento pagamento = new Pagamento();
		pagamento.setId("ID");
		pagamento.setQrcode("QR123");
		pagamento.setStatus(PagamentoStatusEnum.PAGO);
		return pagamento;
	}
	
	private PagamentoModel mockPagamentoModel() {
		PagamentoModel model = new PagamentoModel();
		model.setId("ID");
		model.setStatus(PagamentoStatusEnum.PAGO);
		return model;
	}
}
