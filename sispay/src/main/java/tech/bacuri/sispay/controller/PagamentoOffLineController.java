package tech.bacuri.sispay.controller;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.sispay.dto.NovoPedidoOfflineForm;
import tech.bacuri.sispay.entity.Pagamento;
import tech.bacuri.sispay.service.TransacaoBancoDeDados;
import tech.bacuri.sispay.validator.NovoPedidoOfflineFormValidator;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pagamentos")
public class PagamentoOffLineController {

    private final EntityManager manager;
    //1
    private final TransacaoBancoDeDados transacaoBancoDeDados;
    //1
    private final ObtemValorPedido obtemValorPedido;
    //1
    private final NovoPedidoOfflineFormValidator novoPedidoOfflineFormValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(novoPedidoOfflineFormValidator);
    }

    //1
    @PostMapping("/offline/{idPedido}")
    public ResponseEntity<?> pagamentoOffline(@PathVariable Long idPedido,
                                              @Valid @RequestBody NovoPedidoOfflineForm form) throws Exception {

        BigDecimal valor = obtemValorPedido.executa(idPedido);

        //1 //1
        String uuid = transacaoBancoDeDados.executa(() -> {
            Pagamento novoPagamentoOffline = form.toPagamento(idPedido, valor, manager);
            manager.persist(novoPagamentoOffline);
            return novoPagamentoOffline.getCodigo();
        });

        return ResponseEntity.ok(uuid);
    }
}
