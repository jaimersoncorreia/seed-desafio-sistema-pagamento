package tech.bacuri.sispay.controller;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import tech.bacuri.sispay.dto.NovoPedidoOfflineForm;
import tech.bacuri.sispay.entity.Transacao;
import tech.bacuri.sispay.service.ExecutaTransacao;
import tech.bacuri.sispay.validator.CombinacaoRestauranteUsuarioFormPagamentoValidator;
import tech.bacuri.sispay.validator.FormaPagamentoOfflineValidator;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pagamentos")
public class PagamentoOffLineController {

    private final CombinacaoRestauranteUsuarioFormPagamentoValidator combinacaoRestauranteUsuarioFormPagamentoValidator;
    private final EntityManager manager;
    private final ExecutaTransacao executaTransacao;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new FormaPagamentoOfflineValidator(), combinacaoRestauranteUsuarioFormPagamentoValidator);
    }

    @PostMapping("/offline/{idPedido}")
    public ResponseEntity<?> pagamentoOffline(@PathVariable Long idPedido,
                                              @Valid @RequestBody NovoPedidoOfflineForm form) throws BindException {
        try {
            RestTemplate template = new RestTemplate();
            Map<String, Object> pedido = template.getForObject("http://localhost:8080/sispay/api/pedidos/{idPedido}",
                    Map.class, idPedido);

            assert pedido != null;
            Number valorPedido = (Number) pedido.get("valor");

            Transacao transacao = executaTransacao.executa(() -> {
                Transacao novaTransacaoOffline = form.toTransacao(idPedido, new BigDecimal(valorPedido.toString()), manager);
                manager.persist(novaTransacaoOffline);
                return novaTransacaoOffline;
            });

            return ResponseEntity.ok(transacao.getUuid());
        } catch (HttpClientErrorException e) {
            if (!Objects.equals(e.getStatusCode(), HttpStatus.NOT_FOUND)) throw e;

            BindException bindException = new BindException("", "");
            bindException.reject(null, "Olha, essa id de pedido não existe");
            throw bindException;
        }
    }
}
