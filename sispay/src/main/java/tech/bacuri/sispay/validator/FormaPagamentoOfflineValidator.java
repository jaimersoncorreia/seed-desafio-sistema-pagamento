package tech.bacuri.sispay.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tech.bacuri.sispay.dto.NovoPedidoOfflineForm;

public class FormaPagamentoOfflineValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return NovoPedidoOfflineForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) return;

        NovoPedidoOfflineForm form = (NovoPedidoOfflineForm) target;

        if (!form.isOffline()) {
            errors.rejectValue("formaPagamento", null, "A forma de pagamento deve ser offline");
        }
    }
}
