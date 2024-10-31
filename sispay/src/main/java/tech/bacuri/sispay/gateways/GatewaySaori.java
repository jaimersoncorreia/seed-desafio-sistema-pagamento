package tech.bacuri.sispay.gateways;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tech.bacuri.sispay.entity.Pagamento;
import tech.bacuri.sispay.entity.Resultado;
import tech.bacuri.sispay.entity.Transacao;
import tech.bacuri.sispay.enums.FormaPagamento;
import tech.bacuri.sispay.externo.DadosCompraGenerico;
import tech.bacuri.sispay.service.RequestsGateways;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@Service
public class GatewaySaori extends Gateway {
    private static final Logger log = LoggerFactory.getLogger(GatewaySaori.class);

    @EqualsAndHashCode.Include
    private String id = "gateway-saori";

    private final RequestsGateways requestsGateways;

    @Override
    public boolean aceiteEspecifico(@NotNull @Valid Pagamento novoPagamentoSalvo) {
        FormaPagamento formaPagamento = novoPagamentoSalvo.getFormaPagamento();
        return formaPagamento.pertence(FormaPagamento.VISA, FormaPagamento.MASTER);
    }

    @Override
    public Resultado<Exception, Transacao> processaEspecifico(@NotNull @Valid Pagamento pagamento) {

        log.debug("Processando pagamento por gateway Saori");
        DadosCompraGenerico request = new DadosCompraGenerico(pagamento);
        requestsGateways.saoriProcessa(request);
        return Resultado.sucesso(Transacao.concluida(this));
    }

    @Override
    public BigDecimal custoEspecifico(Pagamento pagamento) {
        return pagamento.getValor().multiply(new BigDecimal("0.05"));
    }
}
