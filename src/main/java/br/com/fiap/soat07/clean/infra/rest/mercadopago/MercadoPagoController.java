package br.com.fiap.soat07.clean.infra.rest.mercadopago;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.soat07.clean.core.domain.enumeration.PagamentoStatusEnum;
import br.com.fiap.soat07.clean.infra.rest.dto.PagamentoDTO;
import br.com.fiap.soat07.clean.infra.rest.mapper.PagamentoMapper;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pagamento/mercadopago")
@RequiredArgsConstructor
@Tag(name = "MercadoPago", description = "Endpoints específicos para o Mercado Pago")
public class MercadoPagoController {
	
	private final WebhookGateway webhookGateway;
	private final PagamentoMapper mapper;

    
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = PagamentoDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid values"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }) })
    @PostMapping
    public ResponseEntity<PagamentoDTO> executePagamento(@RequestBody PagamentoDTO pagamento) {
        if (pagamento == null)
            return ResponseEntity.badRequest().build();
        
        if (pagamento.getPedidoId() % 3 == 0) {
        	pagamento.setStatus(PagamentoStatusEnum.RECUSADO);
        }else if (pagamento.getPedidoId() % 4 == 0) {
        	pagamento.setStatus(PagamentoStatusEnum.NAO_CONCLUIDO);
        }else if (pagamento.getPedidoId() % 5 == 0) {
        	pagamento.setStatus(PagamentoStatusEnum.CANCELADO);
        }else{
        	pagamento.setStatus(PagamentoStatusEnum.PAGO);
        }
        pagamento.setData(LocalDateTime.now());

        webhookGateway.sendResponse(mapper.toDomain(pagamento));
        return ResponseEntity.ok(pagamento);
    }





	

}
