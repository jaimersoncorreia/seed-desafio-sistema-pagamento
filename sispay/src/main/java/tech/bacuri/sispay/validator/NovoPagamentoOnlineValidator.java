package tech.bacuri.sispay.validator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tech.bacuri.sispay.dto.NovoPagamentoOnlineForm;

@RequiredArgsConstructor
@Component
public class NovoPagamentoOnlineValidator implements Validator {

    private final PagamentoGeradoValidator pagamentoGeradoValidator;
    private final CombinacaoRestauranteUsuarioFormaPagamentoValidator combinacaoRestauranteUsuarioFormaPagamentoValidator;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return NovoPagamentoOnlineForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, Errors errors) {
        if (errors.hasErrors()) return;

        pagamentoGeradoValidator.validate(target, errors);
        combinacaoRestauranteUsuarioFormaPagamentoValidator.validate(target, errors);
        new FormaPagamentoOnlineValidator().validate(target, errors);
    }
}
