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
import tech.bacuri.sispay.externo.DadosCartao;
import tech.bacuri.sispay.externo.DadosCartaoSeyaRequest;
import tech.bacuri.sispay.externo.DadosCompraSeyaRequest;
import tech.bacuri.sispay.service.RequestsGateways;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@Service
public class GatewaySeya extends Gateway {
    private final RequestsGateways requestsGateways;

    @EqualsAndHashCode.Include
    private String id = "gateway-seya";

    private static final Logger log = LoggerFactory.getLogger(GatewaySeya.class);

    @Override
    public boolean aceiteEspecifico(@NotNull @Valid Pagamento pagamento) {
        FormaPagamento formaPagamento = pagamento.getFormaPagamento();
        //aqui tem um perigo para o caso de novas formas
        return formaPagamento.isOnline();
    }

    @Override
    public Resultado<Exception, Transacao> processaEspecifico(
            @NotNull @Valid Pagamento pagamento) {
        log.debug("Processando pagamento por gateway Seya");
        DadosCartao dadosCartao = pagamento.getDadosCartao();
        int codigo = requestsGateways.seyaVerifica(new DadosCartaoSeyaRequest(dadosCartao));
        requestsGateways.seyaProcessa(codigo, new DadosCompraSeyaRequest(dadosCartao, pagamento.getValor()));

        return Resultado.sucesso(Transacao.concluida(this));
    }

    @Override
    public BigDecimal custoEspecifico(Pagamento pagamento) {
        return new BigDecimal("6");
    }
}
