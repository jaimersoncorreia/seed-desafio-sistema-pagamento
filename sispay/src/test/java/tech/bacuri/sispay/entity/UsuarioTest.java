package tech.bacuri.sispay.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.bacuri.sispay.enums.FormaPagamento;
import tech.bacuri.sispay.service.RegraFraude;

import java.util.Collection;
import java.util.List;
import java.util.Set;

class UsuarioTest {
    Usuario usuario1 = new Usuario("teste1@bacuri.tech", FormaPagamento.VISA, FormaPagamento.MASTER, FormaPagamento.HIPERCARD);
    Restaurante restaurante1 = new Restaurante("bacuri tech 1", FormaPagamento.VISA, FormaPagamento.MASTER, FormaPagamento.ELO);
    Restaurante restaurante2 = new Restaurante("bacuri tech 2", FormaPagamento.ELO, FormaPagamento.DINHEIRO, FormaPagamento.MAQUINETA);

    @Test
    @DisplayName("Deveria retornar pagamentos aceitos")
    public void teste1() {
        RegraFraude regraFraude = (forma, usuario) -> true;
        Collection<RegraFraude> regras = List.of(regraFraude);

        Set<FormaPagamento> formas = usuario1.filtraFormasPagamento(restaurante1, regras);
        Assertions.assertEquals(2, formas.size());
        Assertions.assertTrue(formas.contains(FormaPagamento.VISA));
        Assertions.assertTrue(formas.contains(FormaPagamento.MASTER));
    }

    @Test
    @DisplayName("Deveria retornar uma lista vazia")
    public void teste2() {
        RegraFraude regraFraude = (formaPagamento, usuario) -> true;
        Collection<RegraFraude> regras = List.of(regraFraude);
        Set<FormaPagamento> formas = usuario1.filtraFormasPagamento(restaurante2, regras);
        Assertions.assertTrue(formas.isEmpty());
    }

    @Test
    @DisplayName("Deveria retornar vazia se alguma regra retornar falso")
    public void teste3() {
        RegraFraude regraNaoAceita = (formaPagamento, usuario) -> false;
        Collection<RegraFraude> regras = List.of(regraNaoAceita);
        Set<FormaPagamento> formas = usuario1.filtraFormasPagamento(restaurante1, regras);
        Assertions.assertTrue(formas.isEmpty());
    }

    @Test
    @DisplayName("Deveria retornar uma lista vazia para usuários que não tem pagamentos combinados com o restaurante e com regra que não aceita")
    public void teste4() {
        RegraFraude regraFraude = (formaPagamento, usuario) -> false;
        Collection<RegraFraude> regras = List.of(regraFraude);
        Set<FormaPagamento> formas = usuario1.filtraFormasPagamento(restaurante2, regras);
        Assertions.assertTrue(formas.isEmpty());
    }
}