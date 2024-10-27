package tech.bacuri.sispay.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.bacuri.sispay.enums.FormaPagamento;
import tech.bacuri.sispay.enums.StatusTransacao;

import java.math.BigDecimal;

class PagamentoTest {

    private final Pagamento pagamento = new Pagamento(
            1L,
            BigDecimal.TEN,
            new Usuario("teste1@bacuri.tech", FormaPagamento.DINHEIRO),
            new Restaurante("teste", FormaPagamento.DINHEIRO),
            StatusTransacao.ESPERANDO_CONFIRMACAO_PAGAMENTO
    );

    @Test
    @DisplayName("deveria vefificar se um pagamento ainda não foi concluído")
    public void teste1() {
        Assertions.assertFalse(pagamento.foiConcluido());
    }

    @Test
    @DisplayName("deveria vefificar se um pagamento foi concluído")
    public void teste2() {
        pagamento.conclui();
        Assertions.assertTrue(pagamento.foiConcluido());
    }

    @Test
    @DisplayName("não deveria deixar concluir uma compra mais de um vez")
    public void teste3() {
        pagamento.conclui();
        Assertions.assertThrows(IllegalStateException.class, pagamento::conclui);
    }
}