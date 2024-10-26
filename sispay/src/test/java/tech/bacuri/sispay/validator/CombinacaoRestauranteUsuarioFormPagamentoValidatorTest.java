package tech.bacuri.sispay.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.Errors;
import tech.bacuri.sispay.dto.NovoPedidoOfflineForm;
import tech.bacuri.sispay.entity.Restaurante;
import tech.bacuri.sispay.entity.Usuario;
import tech.bacuri.sispay.enums.FormaPagamento;
import tech.bacuri.sispay.repository.RestauranteRepository;
import tech.bacuri.sispay.repository.UsuarioRepository;
import tech.bacuri.sispay.service.RegraFraude;

import java.util.Collection;
import java.util.List;

class CombinacaoRestauranteUsuarioFormPagamentoValidatorTest {

    @Test
    @DisplayName("verifica se a combinação entre usuário, restaurante e forma pagamento é válida")
    public void teste1() {
        RestauranteRepository restauranteRepository = Mockito.mock(RestauranteRepository.class);
        UsuarioRepository usuarioRepository = Mockito.mock(UsuarioRepository.class);
        Collection<RegraFraude> regrasFrudes = List.of((formaPagamento, usuario) -> true);

        CombinacaoRestauranteUsuarioFormPagamentoValidator validator = new CombinacaoRestauranteUsuarioFormPagamentoValidator(
                restauranteRepository, usuarioRepository, regrasFrudes);
        Usuario usuarioPagamentoDinheiro = new Usuario("teste1@bacuri.tech", FormaPagamento.DINHEIRO);
        Restaurante restauranteAceitaDinheiro = new Restaurante("restaurante", FormaPagamento.DINHEIRO, FormaPagamento.ELO);
        NovoPedidoOfflineForm form = new NovoPedidoOfflineForm(FormaPagamento.DINHEIRO, 1L, 1L);

        Mockito.when(usuarioRepository.getUsuarioById(1L)).thenReturn(usuarioPagamentoDinheiro);
        Mockito.when(restauranteRepository.getRestaurantesById(1L)).thenReturn(restauranteAceitaDinheiro);
        Errors errors = Mockito.mock(Errors.class);
        Mockito.when(errors.hasErrors()).thenReturn(false);

        validator.validate(form, errors);

        Mockito.verify(errors, Mockito.never()).reject(null, "A combinação entre usuário, restaurante e forma de pagamento não é válida");
    }

    @Test
    @DisplayName("verifica se a combinação entre usuário, restaurante e forma pagamento não é válida")
    public void teste2() {
        RestauranteRepository restauranteRepository = Mockito.mock(RestauranteRepository.class);
        UsuarioRepository usuarioRepository = Mockito.mock(UsuarioRepository.class);
        Collection<RegraFraude> regrasFrudes = List.of((formaPagamento, usuario) -> true);

        CombinacaoRestauranteUsuarioFormPagamentoValidator validator = new CombinacaoRestauranteUsuarioFormPagamentoValidator(
                restauranteRepository, usuarioRepository, regrasFrudes);
        Usuario usuarioPagamentoMaquineta = new Usuario("teste1@bacuri.tech", FormaPagamento.MAQUINETA);
        Restaurante restauranteAceitaDinheiro = new Restaurante("restaurante", FormaPagamento.DINHEIRO, FormaPagamento.ELO);
        NovoPedidoOfflineForm form = new NovoPedidoOfflineForm(FormaPagamento.DINHEIRO, 1L, 1L);

        Mockito.when(usuarioRepository.getUsuarioById(1L)).thenReturn(usuarioPagamentoMaquineta);
        Mockito.when(restauranteRepository.getRestaurantesById(1L)).thenReturn(restauranteAceitaDinheiro);
        Errors errors = Mockito.mock(Errors.class);
        Mockito.when(errors.hasErrors()).thenReturn(false);

        validator.validate(form, errors);

        Mockito.verify(errors).reject(null, "A combinação entre usuário, restaurante e forma de pagamento não é válida");
    }
}