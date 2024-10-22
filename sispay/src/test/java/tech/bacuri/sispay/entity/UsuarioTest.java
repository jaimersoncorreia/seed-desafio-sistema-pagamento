package tech.bacuri.sispay.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.bacuri.sispay.enums.FormaPagamento;
import tech.bacuri.sispay.service.RegraFraude;
import tech.bacuri.sispay.service.RegraUsuarioEmailFraudulento;

import java.util.Collection;
import java.util.List;
import java.util.Set;

class UsuarioTest {
    Usuario teste1 = new Usuario("teste1@bacuri.tech", FormaPagamento.VISA, FormaPagamento.MASTER, FormaPagamento.HIPERCARD);
    Usuario teste2 = new Usuario("teste2@bacuri.tech", FormaPagamento.VISA, FormaPagamento.MASTER, FormaPagamento.HIPERCARD);
    Restaurante bacuriTech1 = new Restaurante("bacuri tech 1", FormaPagamento.VISA, FormaPagamento.MASTER, FormaPagamento.ELO);
    Restaurante bacuriTech2 = new Restaurante("bacuri tech 2", FormaPagamento.ELO, FormaPagamento.DINHEIRO, FormaPagamento.MAQUINETA);
    Collection<RegraFraude> regrasFraude = List.of(new RegraUsuarioEmailFraudulento());

    @Test
    @DisplayName("Deveria retornar pagamentos aceitos")
    public void teste1() {
        Set<FormaPagamento> formas = teste2.filtraFormasPagamento(bacuriTech1, regrasFraude);
        Assertions.assertEquals(2, formas.size());
        Assertions.assertTrue(formas.contains(FormaPagamento.VISA));
        Assertions.assertTrue(formas.contains(FormaPagamento.MASTER));
    }

    @Test
    @DisplayName("Deveria retornar uma lista vazia")
    public void teste2() {
        Set<FormaPagamento> formas = teste2.filtraFormasPagamento(bacuriTech2, regrasFraude);
        Assertions.assertTrue(formas.isEmpty());
    }
}