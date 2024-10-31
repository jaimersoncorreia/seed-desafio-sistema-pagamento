package tech.bacuri.sispay.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import tech.bacuri.sispay.entity.Pagamento;
import tech.bacuri.sispay.entity.Resultado;
import tech.bacuri.sispay.entity.Transacao;
import tech.bacuri.sispay.enums.StatusTransacao;
import tech.bacuri.sispay.gateways.Gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Validated
public class Gateways {

    private final GatewaysOrdenadosPorCusto gatewaysOrdenados;

    public List<Transacao> processa(Pagamento pagamento) {
        ArrayList<Transacao> transacoes = new ArrayList<>();

        for (Gateway gateway : gatewaysOrdenados.ordena(pagamento)) {
            Resultado<Exception, Transacao> possivelNovaTransacao = gateway.processa(pagamento);

            if (possivelNovaTransacao.naoTemErro()) {
                transacoes.add(possivelNovaTransacao.get());
                break;
            }

            Transacao falhou = new Transacao(StatusTransacao.FALHA);
            falhou.setInfoAdicional(Map.of("gateway", gateway, "exception", possivelNovaTransacao.getStackTrace()));
            transacoes.add(falhou);
        }

        Assert.state(!transacoes.isEmpty(), "Pelo menos um gateway deve ter processado o pagamento");
        return transacoes;
    }
}
