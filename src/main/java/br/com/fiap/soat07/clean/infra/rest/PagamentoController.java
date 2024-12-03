package br.com.fiap.soat07.clean.infra.rest;

import java.net.URI;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.fiap.soat07.clean.core.domain.entity.Pagamento;
import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.core.exception.PagamentoNotFoundException;
import br.com.fiap.soat07.clean.infra.rest.dto.CreatePagamentoDTO;
import br.com.fiap.soat07.clean.infra.rest.dto.PagamentoDTO;
import br.com.fiap.soat07.clean.infra.rest.mapper.PagamentoMapper;
import br.com.fiap.soat07.clean.infra.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pagamento")
@RequiredArgsConstructor
public class PagamentoController {

    private final PagamentoService pagamentoService;
    
    private final PagamentoMapper mapper;;

    @Operation(
            operationId = "criar",
            description = "Criar pagamento",
            tags = {"Pagamento"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = PagamentoDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid values"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }) })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagamentoDTO> createPagamento(@RequestBody CreatePagamentoDTO pagamentoDTO) {
        if (pagamentoDTO == null)
            return ResponseEntity.badRequest().build();
        
        Pagamento pagamento = mapper.toDomain(pagamentoDTO);

        Pagamento resultado = pagamentoService.getCreatePagamentoUseCase().executar(pagamento);
        
        URI localURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pagamento.getId()).toUri();
        
        return ResponseEntity.created(localURI).body(mapper.toDTO(resultado));
    }
    
    @Operation(
            operationId = "atualizar",
            description = "Atualizar pagamento",
            tags = {"Pagamento"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = PagamentoDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid values"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }) })
    @PutMapping
    public ResponseEntity<PagamentoDTO> updatePagamento(@RequestBody PagamentoDTO pagamentoDTO) {
        if (pagamentoDTO == null)
            return ResponseEntity.badRequest().build();
        
        Pagamento pagamento = mapper.toDomain(pagamentoDTO);
        
        pagamentoService.getSearchPagamentoUseCase().findById(pagamento.getId()).orElseThrow(() -> new PagamentoNotFoundException(""));       

        Pagamento resultado = pagamentoService.getUpdatePagamentoUseCase().executar(pagamento);
        
        return ResponseEntity.ok().body(mapper.toDTO(resultado));

    }



    @Operation(
            operationId = "consultar status",
            description = "Consultar status do pedido",
            tags = {"Pagamento"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid values"),
            @ApiResponse(responseCode = "404", description = "Pedido not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping(value="/{id}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagamentoStatusEnum> consultarSituacaoDoPagamento(@PathVariable final String id) {

        if (StringUtils.isEmpty(id))
            return ResponseEntity.badRequest().build();
        Pagamento pagamento = pagamentoService.getSearchPagamentoUseCase().findById(id).orElseThrow(() -> new PagamentoNotFoundException(""));

        return ResponseEntity.ok(pagamento.getStatus());
    }
    
    
    @Operation(
            operationId = "consultar pagamento",
            description = "Consultar pagamento do pedido",
            tags = {"Pagamento"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid values"),
            @ApiResponse(responseCode = "404", description = "Pedido not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pagamento> consultarPagamento(@PathVariable final String id) {

        if (StringUtils.isEmpty(id))
            return ResponseEntity.badRequest().build();
        Pagamento pagamento = pagamentoService.getSearchPagamentoUseCase().findById(id).orElseThrow(() -> new PagamentoNotFoundException(""));

        return ResponseEntity.ok(pagamento);
    }
    
    @Operation(
            operationId = "consultar pagamento",
            description = "Consultar pagamento do pedido",
            tags = {"Pagamento"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid values"),
            @ApiResponse(responseCode = "404", description = "Pedido not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }) })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Pagamento>> consultarPagamento(@RequestParam(required = true, defaultValue = "0") Integer page, @RequestParam(required = true, defaultValue = "10") Integer size) {

       
    	Collection<Pagamento> pagamentos = pagamentoService.getSearchPagamentoUseCase().find(page, size);

        return ResponseEntity.ok(pagamentos);
    }
    
    @Operation(
            operationId = "webhook",
            description = "Receber status do pagamento",
            tags = {"Pagamento Webhook"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Webhook", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = PagamentoDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid values"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }) })
    @PostMapping(value="/webhook", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagamentoDTO> webhookPagamento(@RequestBody PagamentoDTO pagamentoDTO) {
        if (pagamentoDTO == null)
            return ResponseEntity.badRequest().build();
        
        Pagamento pagamento = mapper.toDomain(pagamentoDTO);
        
        pagamentoService.getSearchPagamentoUseCase().findById(pagamento.getId()).orElseThrow(() -> new PagamentoNotFoundException(""));       

        Pagamento resultado = pagamentoService.getUpdatePagamentoUseCase().executar(pagamento);
               
        return ResponseEntity.ok().body(mapper.toDTO(resultado));
    }




}
