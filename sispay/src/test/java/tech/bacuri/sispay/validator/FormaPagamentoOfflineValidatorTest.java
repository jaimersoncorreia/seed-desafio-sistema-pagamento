package tech.bacuri.sispay.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.Errors;
import tech.bacuri.sispay.dto.NovoPedidoOfflineForm;
import tech.bacuri.sispay.enums.FormaPagamento;

class FormaPagamentoOfflineValidatorTest {

    @Test
    @DisplayName("se o pedido não é offline então rejeita")
    public void teste1() {
        NovoPedidoOfflineForm form = new NovoPedidoOfflineForm(FormaPagamento.ELO, 1L, 1L);
        Errors errors = Mockito.mock(Errors.class);

        FormaPagamentoOfflineValidator validator = new FormaPagamentoOfflineValidator();
        validator.validate(form, errors);

        Mockito.verify(errors).rejectValue("formaPagamento", null, "A forma de pagamento deve ser offline");
    }

    @Test
    @DisplayName("valida pedidos offline")
    public void teste2() {
        NovoPedidoOfflineForm form = new NovoPedidoOfflineForm(FormaPagamento.DINHEIRO, 1L, 1L);
        Errors errors = Mockito.mock(Errors.class);

        FormaPagamentoOfflineValidator validator = new FormaPagamentoOfflineValidator();
        validator.validate(form, errors);

        Mockito.verify(errors, Mockito.never()).rejectValue("formaPagamento", null, "A forma de pagamento deve ser offline");
    }
}