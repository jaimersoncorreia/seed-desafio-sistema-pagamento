package tech.bacuri.sispay.validator;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.HandlerMapping;
import tech.bacuri.sispay.dto.NovoPedidoOfflineForm;
import tech.bacuri.sispay.entity.Pagamento;
import tech.bacuri.sispay.enums.FormaPagamento;
import tech.bacuri.sispay.repository.PagamentoRepository;

import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class PagamentoGeradoValidatorTest {

    private final PagamentoRepository pagamentoRepository = Mockito.mock(PagamentoRepository.class);
    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    private final PagamentoGeradoValidator validator = new PagamentoGeradoValidator(request, pagamentoRepository);
    private final NovoPedidoOfflineForm form = new NovoPedidoOfflineForm(FormaPagamento.DINHEIRO, 1L, 1L);

    @Test
    @DisplayName("não deveria funcionar caso a variável que identifica o pedido na url não se chame idPedido")
    public void teste1() {
        Map<String, String> variaveisUrl = Map.of("outroIdPedido", "1");
        Mockito.when(request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)).thenReturn(variaveisUrl);
        Errors errors = new BeanPropertyBindingResult(form, "teste");
        Assertions.assertThrows(IllegalStateException.class, () -> {
            validator.validate(request, errors);
        });
    }

    @ParameterizedTest
    @CsvSource({"1,true", "2,false"})
    @DisplayName("deveria gerar erro de validação quando já existe um pagamento com determinado id pedido")
    public void teste2(String idPedido, boolean esperado) {
        Mockito.when(pagamentoRepository.getPagamentoByIdPedido(1L)).thenReturn(Mockito.mock(Pagamento.class));
        Map<String, String> variaveisUrl = Map.of("idPedido", idPedido);
        Mockito.when(request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)).thenReturn(variaveisUrl);
        Errors errors = new BeanPropertyBindingResult(form, "teste");
        validator.validate(form, errors);
        Assertions.assertEquals(esperado, errors.hasGlobalErrors());
    }

    @Test
    @DisplayName("deveria aceitar pedidos novos")
    public void teste3() {
        Mockito.when(pagamentoRepository.getPagamentoByIdPedido(1L)).thenReturn(Mockito.mock(Pagamento.class));
        Map<String, String> variaveisUrl = Map.of("idPedido", "2");
        Mockito.when(request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE)).thenReturn(variaveisUrl);
        Errors errors = new BeanPropertyBindingResult(form, "teste");
        validator.validate(form, errors);
        Assertions.assertFalse(errors.hasGlobalErrors());
    }
}