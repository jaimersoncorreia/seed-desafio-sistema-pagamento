package tech.bacuri.sispay.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.bacuri.sispay.entity.Pagamento;
import tech.bacuri.sispay.gateways.Gateway;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class GatewaysOrdenadosPorCusto {

    private final Set<Gateway> gateways;

    public List<Gateway> ordena(Pagamento pagamento) {
        return gateways.stream().filter(gateway -> gateway.aceita(pagamento))
                .sorted(Comparator.comparing(gateway -> gateway.custo(pagamento)))
                .toList();
    }
}
