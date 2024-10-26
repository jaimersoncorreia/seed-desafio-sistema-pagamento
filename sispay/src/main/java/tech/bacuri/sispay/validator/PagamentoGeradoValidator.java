package tech.bacuri.sispay.validator;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.HandlerMapping;
import tech.bacuri.sispay.dto.NovoPedidoOfflineForm;
import tech.bacuri.sispay.entity.Pagamento;
import tech.bacuri.sispay.repository.PagamentoRepository;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class PagamentoGeradoValidator implements Validator {
    private final HttpServletRequest request;
    private final PagamentoRepository pagamentoRepository;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return NovoPedidoOfflineForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, Errors errors) {
        if (errors.hasErrors()) return;

        @SuppressWarnings("unchecked")
        Map<String, String> variaveisUrl = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String paramIdPedido = variaveisUrl.get("idPedido");

        Assert.state(StringUtils.hasText(paramIdPedido), "Para este validator funcionar, o PathVariable que representa o pedido é idPedido");
        Long idPedido = Long.valueOf(paramIdPedido);

        Pagamento pagamento = pagamentoRepository.getPagamentoByIdPedido(idPedido);
        if (Objects.nonNull(pagamento))
            errors.reject(null, "Já existe um pagamento iniciado para este pedido");
    }
}
