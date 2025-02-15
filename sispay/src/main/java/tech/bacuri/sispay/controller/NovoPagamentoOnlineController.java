package tech.bacuri.sispay.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.sispay.dto.NovoPagamentoOnlineForm;
import tech.bacuri.sispay.entity.Pagamento;
import tech.bacuri.sispay.entity.Transacao;
import tech.bacuri.sispay.service.Gateways;
import tech.bacuri.sispay.service.IniciaPagamento;
import tech.bacuri.sispay.service.TransacaoBancoDeDados;
import tech.bacuri.sispay.validator.NovoPagamentoOnlineValidator;

import java.util.List;
/*
TODO: fazer esses testes
Como Alberto refatorou os testes 3 meses depois: Parte 1, parte 2, parte 3, parte 4*/
@RequiredArgsConstructor
@RestController
@RequestMapping("/pagamentos/online")
public class NovoPagamentoOnlineController {
    private final NovoPagamentoOnlineValidator novoPagamentoOnlineValidator;
    private final IniciaPagamento iniciaPagamento;
    private final Gateways gateways;
    private final TransacaoBancoDeDados transacaoBancoDeDados;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(novoPagamentoOnlineValidator);
    }

    @PostMapping("/{idPedido}")
    public void paga(@PathVariable Long idPedido,
                     @Valid @RequestBody NovoPagamentoOnlineForm form) throws BindException {
        Pagamento novoPagamentoSalvo = iniciaPagamento.executa(idPedido, form).get();

        List<Transacao> transacoesGeradas = gateways.processa(novoPagamentoSalvo);

        transacaoBancoDeDados.executa(() -> {
            novoPagamentoSalvo.adicionaTransacao(transacoesGeradas);
            return null;
        });
    }
}
