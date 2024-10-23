package tech.bacuri.sispay.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.bacuri.sispay.entity.Usuario;
import tech.bacuri.sispay.enums.FormaPagamento;

class RegraUsuarioEmailFraudulentoTest {
    Usuario usuario1 = new Usuario("teste1@bacuri.tech", FormaPagamento.ELO);
    Usuario usuario2 = new Usuario("teste2@bacuri.tech", FormaPagamento.ELO);

    @Test
    @DisplayName("deveria aceitar todo pagamento offline")
    public void teste1() {
        RegraUsuarioEmailFraudulento regra = new RegraUsuarioEmailFraudulento();
        boolean aceita = regra.aceita(FormaPagamento.DINHEIRO, usuario2);
        Assertions.assertTrue(aceita);
    }

    @Test
    @DisplayName("deveria aceitar todo pagamento online para usuarios diferentes de teste1")
    public void teste2() {
        RegraUsuarioEmailFraudulento regra = new RegraUsuarioEmailFraudulento();
        boolean aceita = regra.aceita(FormaPagamento.ELO, usuario2);
        Assertions.assertTrue(aceita);
    }

    @Test
    @DisplayName("deveria bloquear pagamento online de teste1")
    public void teste3() {
        RegraUsuarioEmailFraudulento regra = new RegraUsuarioEmailFraudulento();
        boolean aceita = regra.aceita(FormaPagamento.ELO, usuario1);
        Assertions.assertFalse(aceita);
    }

}