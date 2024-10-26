package tech.bacuri.sispay.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
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
import java.util.stream.Stream;

class CombinacaoRestauranteUsuarioFormPagamentoValidatorTest {

    RestauranteRepository restauranteRepository = Mockito.mock(RestauranteRepository.class);
    UsuarioRepository usuarioRepository = Mockito.mock(UsuarioRepository.class);
    Collection<RegraFraude> regrasFrudes = List.of((formaPagamento, usuario) -> true);
    CombinacaoRestauranteUsuarioFormPagamentoValidator validator = new CombinacaoRestauranteUsuarioFormPagamentoValidator(
            restauranteRepository, usuarioRepository, regrasFrudes);

    public static Stream<Arguments> geradorTeste1() {
        return Stream.of(
                Arguments.of(FormaPagamento.DINHEIRO, List.of(FormaPagamento.DINHEIRO), List.of(FormaPagamento.DINHEIRO), false),
                Arguments.of(FormaPagamento.DINHEIRO, List.of(FormaPagamento.DINHEIRO), List.of(FormaPagamento.VISA), true),
                Arguments.of(FormaPagamento.DINHEIRO, List.of(FormaPagamento.DINHEIRO), List.of(FormaPagamento.DINHEIRO, FormaPagamento.MASTER), false),
                Arguments.of(FormaPagamento.DINHEIRO, List.of(FormaPagamento.DINHEIRO, FormaPagamento.MAQUINETA), List.of(FormaPagamento.ELO, FormaPagamento.MASTER), true)
        );
    }

    @ParameterizedTest
    @MethodSource("geradorTeste1")
    @DisplayName("verifica se a combinação entre usuário, restaurante e forma pagamento é válida")
    public void teste1(FormaPagamento formaPagamentoEscolhida,
                       List<FormaPagamento> formasPagamentoRestaurante,
                       List<FormaPagamento> formasPagamentoUsuario,
                       boolean esperado) {

        Usuario usuarioPagamentoDinheiro = new Usuario("teste1@bacuri.tech", formasPagamentoUsuario.toArray(new FormaPagamento[]{}));
        Restaurante restauranteAceitaDinheiro = new Restaurante("restaurante", formasPagamentoRestaurante.toArray(new FormaPagamento[]{}));
        NovoPedidoOfflineForm form = new NovoPedidoOfflineForm(formaPagamentoEscolhida, 1L, 1L);

        Mockito.when(usuarioRepository.getUsuarioById(1L)).thenReturn(usuarioPagamentoDinheiro);
        Mockito.when(restauranteRepository.getRestaurantesById(1L)).thenReturn(restauranteAceitaDinheiro);
        Errors errors = new BeanPropertyBindingResult(form, "teste");

        validator.validate(form, errors);

        Assertions.assertEquals(esperado, errors.hasGlobalErrors());
    }
}