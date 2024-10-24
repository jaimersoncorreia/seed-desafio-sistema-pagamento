package tech.bacuri.sispay.externo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/pedidos")
public class EndPointsExternos {
    private final AtomicInteger ids = new AtomicInteger();

    @GetMapping("/{idPedido}")
    public ResponseEntity<?> valorPedido(@PathVariable Long idPedido) {
        System.out.println("idPedido = " + idPedido);
        if (ids.getAndIncrement() % 3 == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        String[] valores = new String[]{"50.00", "150.00", "60", "200"};
        int posicao = new Random().nextInt(4);
        return ResponseEntity.ok(Map.of("valor", new BigDecimal(valores[posicao])));
    }
}
