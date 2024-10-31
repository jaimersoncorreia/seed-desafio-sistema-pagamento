package tech.bacuri.sispay.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import tech.bacuri.sispay.entity.Pagamento;
import tech.bacuri.sispay.entity.Restaurante;
import tech.bacuri.sispay.entity.Usuario;
import tech.bacuri.sispay.enums.FormaPagamento;
import tech.bacuri.sispay.enums.StatusTransacao;
import tech.bacuri.sispay.repository.PagamentoRepository;

import java.math.BigDecimal;
import java.util.Optional;

class ConcluiCompraOfflineControllerTest {
    private final PagamentoRepository pagamentoRepository = Mockito.mock(PagamentoRepository.class);
    private final ConcluiCompraOfflineController controller = new ConcluiCompraOfflineController(pagamentoRepository);
    private final Pagamento pagamento = Pagamento.offline(
            1L,
            BigDecimal.TEN,
            FormaPagamento.DINHEIRO,
            new Usuario("teste1@bacuri.tech", FormaPagamento.DINHEIRO),
            new Restaurante("teste", FormaPagamento.DINHEIRO),
            StatusTransacao.ESPERANDO_CONFIRMACAO_PAGAMENTO
    );

    @Test
    @DisplayName("deveria retornar 404 para um pagamento que não existe")
    public void teste1() {
        Mockito.when(pagamentoRepository.findByCodigo("123456"))
                .thenReturn(Optional.empty());

        try {
            controller.finaliza("123456");
            Assertions.fail();
        } catch (ResponseStatusException e) {
            Assertions.assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }

    @Test
    @DisplayName("deveria retornar 400 para um pagamento que jpa foi concluído")
    public void teste2() {
        pagamento.conclui();
        Mockito.when(pagamentoRepository.findByCodigo("1234567"))
                .thenReturn(Optional.of(pagamento));

        try {
            controller.finaliza("1234567");
            Assertions.fail();
        } catch (ResponseStatusException e) {
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }
    }

    @Test
    @DisplayName("deveria deveria concluir uma compra com sucesso")
    public void teste3() {
        Pagamento pagamentoObservavel = Mockito.spy(pagamento);
        Mockito.when(pagamentoRepository.findByCodigo("12345678"))
                .thenReturn(Optional.of(pagamentoObservavel));

        controller.finaliza("12345678");
        Mockito.verify(pagamentoObservavel).conclui();
    }
}