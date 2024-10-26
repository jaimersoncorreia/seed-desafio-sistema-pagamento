package tech.bacuri.sispay.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import tech.bacuri.sispay.dto.NovoPedidoOfflineForm;
import tech.bacuri.sispay.enums.FormaPagamento;

import java.util.stream.Stream;

class FormaPagamentoOfflineValidatorTest {

    private static Stream<Arguments> geradorTeste1() {
        return Stream.of(
                Arguments.of(FormaPagamento.DINHEIRO, false),
                Arguments.of(FormaPagamento.ELO, true)
        );
    }

    @DisplayName("verifica pagamento offline")
    @ParameterizedTest
    @MethodSource("geradorTeste1")
    public void teste0(FormaPagamento forma, boolean esperado) {
        NovoPedidoOfflineForm form = new NovoPedidoOfflineForm(forma, 1L, 1L);
        Errors errors = new BeanPropertyBindingResult(form, "teste");

        FormaPagamentoOfflineValidator validator = new FormaPagamentoOfflineValidator();
        validator.validate(form, errors);

        Assertions.assertEquals(esperado, errors.hasFieldErrors("formaPagamento"));
    }
}