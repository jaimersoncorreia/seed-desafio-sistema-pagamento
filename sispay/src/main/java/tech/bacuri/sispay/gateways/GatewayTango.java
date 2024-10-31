package tech.bacuri.sispay.gateways;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.bacuri.sispay.entity.Pagamento;
import tech.bacuri.sispay.entity.Resultado;
import tech.bacuri.sispay.entity.Transacao;
import tech.bacuri.sispay.externo.DadosCompraGenerico;
import tech.bacuri.sispay.service.RequestsGateways;

import java.math.BigDecimal;
import java.math.RoundingMode;

@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@Service
public class GatewayTango extends Gateway {
    private final RequestsGateways requestsGateways;

    @EqualsAndHashCode.Include
    private String id = "gateway-tango";

    @Override
    public boolean aceiteEspecifico(@NotNull @Valid Pagamento pagamento) {
        return pagamento.getFormaPagamento().isOnline();
    }

    @Override
    public Resultado<Exception, Transacao> processaEspecifico(
            @NotNull @Valid Pagamento pagamento) {
        requestsGateways.tangoProcessa(new DadosCompraGenerico(pagamento));
        return Resultado.sucesso(Transacao.concluida(this));
    }

    @Override
    public BigDecimal custoEspecifico(@NotNull @Valid Pagamento pagamento) {
        // isso aqui precisa ser definido pelo negócio
        BigDecimal valor = pagamento.getValor().setScale(2, RoundingMode.HALF_EVEN);

        if (valor.compareTo(new BigDecimal("100.00")) <= 0)
            return new BigDecimal("4");

        return valor.multiply(new BigDecimal("0.06"));
    }
}
