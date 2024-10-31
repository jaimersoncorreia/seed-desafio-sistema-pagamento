package tech.bacuri.sispay.validator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tech.bacuri.sispay.dto.NovoPagamentoOnlineForm;

@RequiredArgsConstructor
@Component
public class FormaPagamentoOnlineValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return NovoPagamentoOnlineForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, Errors errors) {
        if (errors.hasErrors()) return;

        NovoPagamentoOnlineForm form = (NovoPagamentoOnlineForm) target;
        if (!form.pagamentoOnline())
            errors.rejectValue("formaPagamento", null, "apenas pagamento online");
    }
}
