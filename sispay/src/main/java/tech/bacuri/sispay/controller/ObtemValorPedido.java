package tech.bacuri.sispay.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@Component
public class ObtemValorPedido {
    public BigDecimal executa(Long idPedido) throws BindException {
        try {
            RestTemplate template = new RestTemplate();
            var pedido = template.getForObject("http://localhost:8080/sispay/api/pedidos/{idPedido}", Map.class, idPedido);
            Assert.notNull(pedido, "não deveria ter vindo nullo");
            return new BigDecimal(pedido.get("valor").toString());
        } catch (HttpClientErrorException e) {
            if (!Objects.equals(e.getStatusCode(), HttpStatus.NOT_FOUND)) throw e;
            BindException bindException = new BindException("", "");
            bindException.reject(null, "Olha, essa id de pedido não existe");
            throw bindException;
        }
    }
}
