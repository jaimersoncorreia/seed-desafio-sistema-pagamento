package tech.bacuri.sispay.service;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import tech.bacuri.sispay.controller.NovoPagamentoOnlineController;
import tech.bacuri.sispay.controller.ObtemValorPedido;
import tech.bacuri.sispay.dto.NovoPagamentoOnlineForm;
import tech.bacuri.sispay.entity.Pagamento;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class IniciaPagamento {

    //1
    private final ObtemValorPedido obtemValorPedido;
    //1
    private final TransacaoBancoDeDados transacaoBancoDeDados;
    private final EntityManager manager;

    public Persisted<Pagamento> executa(Long idPedido, @Valid NovoPagamentoOnlineForm form) throws BindException {
        ForceSiteCall.fromExactlyPoint(NovoPagamentoOnlineController.class);
        //1
        BigDecimal valor = obtemValorPedido.executa(idPedido);

        //1 //1
        Pagamento novoPagamentoSalvo = transacaoBancoDeDados.executa(() -> {
            Pagamento novoPagamento = form.toPagamento(idPedido, valor, manager);
            manager.persist(novoPagamento);
            return novoPagamento;
        });

        return new Persisted<>(novoPagamentoSalvo);
    }
}
