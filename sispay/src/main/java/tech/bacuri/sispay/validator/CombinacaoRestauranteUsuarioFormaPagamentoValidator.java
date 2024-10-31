package tech.bacuri.sispay.validator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tech.bacuri.sispay.dto.NovoPedidoOfflineForm;
import tech.bacuri.sispay.entity.Restaurante;
import tech.bacuri.sispay.entity.Usuario;
import tech.bacuri.sispay.repository.RestauranteRepository;
import tech.bacuri.sispay.repository.UsuarioRepository;
import tech.bacuri.sispay.service.RegraFraude;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class CombinacaoRestauranteUsuarioFormaPagamentoValidator implements Validator {
    private final RestauranteRepository restauranteRepository;
    private final UsuarioRepository usuarioRepository;
    private final Collection<RegraFraude> regrasFraudes;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return NovoPedidoOfflineForm.class.isAssignableFrom(clazz);
    }

    @Transactional
    @Override
    public void validate(@NonNull Object target, Errors errors) {
        if (errors.hasErrors()) return;
        TemCombinacaoUsuarioRestauranteFormaPagamento form = (TemCombinacaoUsuarioRestauranteFormaPagamento) target;
        Usuario usuario = usuarioRepository.getUsuarioById(form.getIdUsuario());
        Restaurante restaurante = restauranteRepository.getRestaurantesById(form.getIdRestaurante());
        if (!usuario.podePagar(restaurante, form.getFormaPagamento(), regrasFraudes))
            errors.reject(null, "A combinação entre usuário, restaurante e forma de pagamento não é válida");
    }
}
