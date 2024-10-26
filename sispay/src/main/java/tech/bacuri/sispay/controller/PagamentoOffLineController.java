package tech.bacuri.sispay.controller;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.sispay.dto.NovoPedidoOfflineForm;
import tech.bacuri.sispay.entity.Transacao;
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
            Transacao novaTransacaoOffline = form.toTransacao(idPedido, valor, manager);
            manager.persist(novaTransacaoOffline);
            return novaTransacaoOffline.getUuid();
        });

        return ResponseEntity.ok(uuid);
    }
}
