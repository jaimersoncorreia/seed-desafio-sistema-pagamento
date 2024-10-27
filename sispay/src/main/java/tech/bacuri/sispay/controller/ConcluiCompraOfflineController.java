package tech.bacuri.sispay.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import tech.bacuri.sispay.entity.Pagamento;
import tech.bacuri.sispay.repository.PagamentoRepository;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pagamentos")
public class ConcluiCompraOfflineController {
    private final PagamentoRepository pagamentoRepository;

    @Transactional
    @PostMapping("/offline/{codigoPagamento}/finaliza")
    public void finaliza(@PathVariable String codigoPagamento) {
        Optional<Pagamento> possivelPagamento = pagamentoRepository.findByCodigo(codigoPagamento);
        if (possivelPagamento.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Pagamento pagamento = possivelPagamento.get();

        if (pagamento.foiConcluido())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        pagamento.conclui();
    }
}
