package br.com.fiap.soat07.clean.infra.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.callibrity.logging.test.LogTracker;
import com.callibrity.logging.test.LogTrackerStub;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.usecase.pagamento.CreatePagamentoUseCase;
import br.com.fiap.soat07.clean.core.usecase.pagamento.SearchPagamentoUseCase;
import br.com.fiap.soat07.clean.core.usecase.pagamento.UpdatePagamentoUseCase;
import br.com.fiap.soat07.clean.infra.rest.dto.CreatePagamentoDTO;
import br.com.fiap.soat07.clean.infra.rest.dto.PagamentoDTO;
import br.com.fiap.soat07.clean.infra.rest.mapper.PagamentoMapper;
import br.com.fiap.soat07.clean.infra.service.PagamentoService;

public class PagamentoControllerTest {
	
	private MockMvc mockMvc;

	@RegisterExtension
	LogTrackerStub logTracker = LogTrackerStub.create().recordForLevel(LogTracker.LogLevel.INFO)
			.recordForType(PagamentoController.class);

	@Mock
	private PagamentoService pagamentoService;
	
	@Mock
	private CreatePagamentoUseCase createPagamentoUseCase;
	
	//@Mock
    //private DeletePagamentoUseCase deletePagamentoUseCase;
	
	@Mock
    private UpdatePagamentoUseCase updatePagamentoUseCase;
	
	@Mock
    private SearchPagamentoUseCase searchPagamentoUseCase;

	PagamentoMapper mapper = PagamentoMapper.INSTANCE;

	AutoCloseable openMocks;

	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		PagamentoController pagamentoController = new PagamentoController(pagamentoService, mapper);
		mockMvc = MockMvcBuilders.standaloneSetup(pagamentoController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler())
				.addFilter((request, response, chain) -> {
					response.setCharacterEncoding("UTF-8");
					chain.doFilter(request, response);
				}, "/*").build();
	}

	@AfterEach
	void tearDown() throws Exception {
		openMocks.close();
	}

	@Nested
	class CreatePagamento {
		@Test
		void shouldTestCreatePagamento() throws Exception {
			when(pagamentoService.getCreatePagamentoUseCase()).thenReturn(createPagamentoUseCase);
			when(createPagamentoUseCase.executar(any(Pagamento.class))).thenReturn(mockPagamento());
	
			mockMvc.perform(
					post("/pagamento")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(mockCreatePagamentoDTO())))
	        //        .andDo(print())
					.andExpect(status().isCreated());
			verify(pagamentoService, times(1)).getCreatePagamentoUseCase();
		}

		@Test
		void shouldTestCreatePagamentoWithNull() throws Exception {
			when(pagamentoService.getCreatePagamentoUseCase()).thenReturn(createPagamentoUseCase);
			when(createPagamentoUseCase.executar(any(Pagamento.class))).thenReturn(mockPagamento());
			CreatePagamentoDTO dto = null;
			mockMvc.perform(
					post("/pagamento")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(dto)))
	        //        .andDo(print())
					.andExpect(status().isBadRequest());

		}
	}
	

	@Nested
	class UpdatePagamento {
		@Test
		void shouldTestUpdatePagamento() throws Exception {
			when(pagamentoService.getUpdatePagamentoUseCase()).thenReturn(updatePagamentoUseCase);
			when(pagamentoService.getSearchPagamentoUseCase()).thenReturn(searchPagamentoUseCase);
			when(searchPagamentoUseCase.findById(anyString())).thenReturn(Optional.of(mockPagamento()));
			
			when(pagamentoService.getUpdatePagamentoUseCase().executar(any(Pagamento.class))).thenReturn(mockPagamento());
	
			mockMvc.perform(
					put("/pagamento")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(mockPagamentoDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(pagamentoService, times(2)).getUpdatePagamentoUseCase();
		}
	}
	
	/*
	@Nested
	class DeletePagamento {
		@Test
		void shouldTestDeletePagamento() throws Exception {
			when(pagamentoService.getDeletePagamentoUseCase()).thenReturn(deletePagamentoUseCase);
			when(pagamentoService.getSearchPagamentoUseCase()).thenReturn(searchPagamentoUseCase);
			when(pagamentoService.getSearchPagamentoUseCase().findById(anyLong())).thenReturn(Optional.of(mockPagamento()));
			
			doNothing().when(deletePagamentoUseCase).execute(any(Pagamento.class));
	
			mockMvc.perform(
					delete("/pagamento/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(mockPagamentoDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(pagamentoService, times(1)).getDeletePagamentoUseCase();
		}
		
		@Test
		void shouldTestDeletePagamentoNotFound() throws Exception {
			when(pagamentoService.getDeletePagamentoUseCase()).thenReturn(deletePagamentoUseCase);
			when(pagamentoService.getSearchPagamentoUseCase()).thenReturn(searchPagamentoUseCase);
			when(pagamentoService.getSearchPagamentoUseCase().findById(anyLong())).thenReturn(Optional.empty());
			
			doNothing().when(deletePagamentoUseCase).execute(any(Pagamento.class));
	
			mockMvc.perform(
					delete("/pagamento/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(pagamentoService, times(0)).getDeletePagamentoUseCase();
		}
		
		@Test
		void shouldTestDeletePagamentoWithIdNull() throws Exception {
			when(pagamentoService.getDeletePagamentoUseCase()).thenReturn(deletePagamentoUseCase);
			when(pagamentoService.getSearchPagamentoUseCase()).thenReturn(searchPagamentoUseCase);
			when(pagamentoService.getSearchPagamentoUseCase().findById(anyLong())).thenReturn(Optional.empty());
			
			doNothing().when(deletePagamentoUseCase).execute(any(Pagamento.class));
	
			Long id = null;
			mockMvc.perform(
					delete("/pagamento/{id}", id)
					.contentType(MediaType.APPLICATION_JSON))
	        //        .andDo(print())
					.andExpect(status().isNotFound());
			verify(pagamentoService, times(0)).getDeletePagamentoUseCase();
		}
	}
	*/
	@Nested
	class FindPagamento {
		@Test
		void shouldTestGetPagamento() throws Exception {
			when(pagamentoService.getSearchPagamentoUseCase()).thenReturn(searchPagamentoUseCase);
			when(pagamentoService.getSearchPagamentoUseCase().findById(anyString())).thenReturn(Optional.of(mockPagamento()));
	
			mockMvc.perform(
					get("/pagamento/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(mockPagamentoDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(pagamentoService, times(2)).getSearchPagamentoUseCase();
		}
		
		@Test
		void shouldTestGetPagamentoNotFound() throws Exception {
			when(pagamentoService.getSearchPagamentoUseCase()).thenReturn(searchPagamentoUseCase);
			when(pagamentoService.getSearchPagamentoUseCase().findById(anyString())).thenReturn(Optional.empty());
	
			mockMvc.perform(
					get("/pagamento/{id}", 1L)
					.contentType(MediaType.APPLICATION_JSON))
	        //        .andDo(print())
					.andExpect(status().isNotFound());
			verify(pagamentoService, times(2)).getSearchPagamentoUseCase();
		}
		
		@Test
		void shouldTestGetPagamentoWithIdNull() throws Exception {
			when(pagamentoService.getSearchPagamentoUseCase()).thenReturn(searchPagamentoUseCase);
			when(pagamentoService.getSearchPagamentoUseCase().findById(anyString())).thenReturn(Optional.empty());
	
			Long id = null;
			mockMvc.perform(
					get("/pagamento/{id}", id)
					.contentType(MediaType.APPLICATION_JSON))
	        //        .andDo(print())
					.andExpect(status().isNotFound());
			verify(pagamentoService, times(1)).getSearchPagamentoUseCase();
		}
		
		@Test
		void shouldTestGetPagamentoStatus() throws Exception {
			when(pagamentoService.getSearchPagamentoUseCase()).thenReturn(searchPagamentoUseCase);
			when(searchPagamentoUseCase.findById(anyString())).thenReturn(Optional.of(mockPagamento()));
	
			mockMvc.perform(
					get("/pagamento/{id}/status", "1")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(mockPagamentoDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(pagamentoService, times(1)).getSearchPagamentoUseCase();
		}
		
		@Test
		void shouldTestGetPagamentoStatusWithIdNull() throws Exception {
			when(pagamentoService.getSearchPagamentoUseCase()).thenReturn(searchPagamentoUseCase);
			when(searchPagamentoUseCase.findById(anyString())).thenReturn(Optional.of(mockPagamento()));
	
			PagamentoDTO dto = mockPagamentoDTO();
			String id = null;
			mockMvc.perform(
					get("/pagamento/{id}/status", id)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(dto)))
	        //        .andDo(print())
					.andExpect(status().isNotFound());

		}
		
		@Test
		void shouldTestGetPagamentoStatusWithIdEmpty() throws Exception {
			when(pagamentoService.getSearchPagamentoUseCase()).thenReturn(searchPagamentoUseCase);
			when(searchPagamentoUseCase.findById(anyString())).thenReturn(Optional.of(mockPagamento()));
	
			PagamentoDTO dto = mockPagamentoDTO();
			String id = "";
			mockMvc.perform(
					get("/pagamento/{id}/status", id)
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(dto)))
	        //        .andDo(print())
					.andExpect(status().isNotFound());

		}
		
		@Test
		void shouldTestGetPagamentos() throws Exception {
			when(pagamentoService.getSearchPagamentoUseCase()).thenReturn(searchPagamentoUseCase);
			when(searchPagamentoUseCase.find(anyInt(), anyInt())).thenReturn(mockPagamentoLista());
	
			mockMvc.perform(
					get("/pagamento")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(mockPagamentoDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(pagamentoService, times(1)).getSearchPagamentoUseCase();
		}
		
	}
	
	@Nested
	class Webhook {
		@Test
		void shouldTestWebhookPagamento() throws Exception {
			when(pagamentoService.getUpdatePagamentoUseCase()).thenReturn(updatePagamentoUseCase);
			when(updatePagamentoUseCase.executar(any(Pagamento.class))).thenReturn(mockPagamento());
			
			when(pagamentoService.getSearchPagamentoUseCase()).thenReturn(searchPagamentoUseCase);
			when(searchPagamentoUseCase.findById(anyString())).thenReturn(Optional.of(mockPagamento()));
	
			mockMvc.perform(
					post("/pagamento/webhook")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJsonString(mockPagamentoDTO())))
	        //        .andDo(print())
					.andExpect(status().isOk());
			verify(pagamentoService, times(1)).getUpdatePagamentoUseCase();
		}
	}
	
	private Collection<Pagamento> mockPagamentoLista() {
		List<Pagamento> pagamento = new ArrayList<>();
		pagamento.add(mockPagamento());
		return pagamento;
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
	
	private CreatePagamentoDTO mockCreatePagamentoDTO() {
			
		return CreatePagamentoDTO.builder()
				.pedidoId(1L)
				.status(PagamentoStatusEnum.SOLICITADO)
				.data(LocalDateTime.now())
				.build();

	}
	
	public static String asJsonString(final Object obj) {
		try {
			 ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
			 mapper.findAndRegisterModules();
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
