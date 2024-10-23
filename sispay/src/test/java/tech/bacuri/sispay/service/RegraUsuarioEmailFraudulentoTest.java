package tech.bacuri.sispay.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tech.bacuri.sispay.entity.Usuario;
import tech.bacuri.sispay.enums.FormaPagamento;

import java.util.stream.Stream;

class RegraUsuarioEmailFraudulentoTest {

    @DisplayName("deveria lidar com todo tipo de pagamento")
    @ParameterizedTest
    @MethodSource("geradorTeste1")
    public void teste1(FormaPagamento forma, Usuario usuario, boolean esperado) {
        RegraUsuarioEmailFraudulento regra = new RegraUsuarioEmailFraudulento();
        boolean aceita = regra.aceita(forma, usuario);
        Assertions.assertEquals(esperado, aceita);
    }

    public static Stream<Arguments> geradorTeste1() {
        Usuario invalido = new Usuario("teste1@bacuri.tech", FormaPagamento.ELO);
        Usuario valido = new Usuario("teste2@bacuri.tech", FormaPagamento.ELO);
        return Stream.of(
                Arguments.of(FormaPagamento.DINHEIRO, valido, true),
                Arguments.of(FormaPagamento.VISA, valido, true),
                Arguments.of(FormaPagamento.VISA, invalido, false)
        );
    }
}
