package tech.bacuri.sispay.validator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tech.bacuri.sispay.dto.NovoPedidoOfflineForm;

@Component
@RequiredArgsConstructor
public class NovoPedidoOfflineFormValidator implements Validator {
    private final CombinacaoRestauranteUsuarioFormPagamentoValidator combinacaoRestauranteUsuarioFormPagamentoValidator;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return NovoPedidoOfflineForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, Errors errors) {
        if (errors.hasErrors()) return;

        combinacaoRestauranteUsuarioFormPagamentoValidator.validate(target, errors);
        new FormaPagamentoOfflineValidator().validate(target, errors);
    }
}
