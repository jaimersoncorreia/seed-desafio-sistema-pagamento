package tech.bacuri.sispay.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.bacuri.sispay.enums.FormaPagamento;

class NovoPedidoOfflineFormTest {

    @Test
    @DisplayName("deveria verificar se um novo pedido é offline")
    public void teste1() {
        NovoPedidoOfflineForm form = new NovoPedidoOfflineForm(FormaPagamento.DINHEIRO, 1L, 1L);
        Assertions.assertTrue(form.isOffline());
    }

    @Test
    @DisplayName("deveria verificar se um novo pedido não é offline")
    public void teste2() {
        NovoPedidoOfflineForm form = new NovoPedidoOfflineForm(FormaPagamento.ELO, 1L, 1L);
        Assertions.assertFalse(form.isOffline());
    }
}