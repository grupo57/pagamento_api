package br.com.fiap.soat07.clean.infra.repository.mongo;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.soat07.clean.infra.repository.mongo.model.PagamentoModel;
import br.com.fiap.soat07.clean.infra.rest.mapper.PagamentoMapper;
import jakarta.persistence.TypedQuery;

@ExtendWith(MockitoExtension.class)
public class PagamentoRepositoryImplTest {
	
	@InjectMocks
	//ProdutoRepository repository;
	 
	

	PagamentoMapper produtoMapper = PagamentoMapper.INSTANCE;
	
	@Mock
	TypedQuery<PagamentoModel> typedQuery;
	
	@Mock
	Query<PagamentoModel> query;
	
	@Mock
	Session session;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		//ReflectionTestUtils.setField(repository, "produtoMapper", produtoMapper);
	}
	
	@Test
	void shouldTestFindByIdNoResultException() {		

		//when(entityManager.createQuery(anyString(), eq(PagamentoModel.class))).thenReturn(typedQuery);
		
		
		//assertEquals(Optional.empty(), repository.findById(1L));
	}
	
	
}
