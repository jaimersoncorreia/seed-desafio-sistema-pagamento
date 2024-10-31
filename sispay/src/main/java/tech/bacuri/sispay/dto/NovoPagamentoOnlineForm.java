package tech.bacuri.sispay.dto;

import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.CreditCardNumber;
import tech.bacuri.sispay.entity.Pagamento;
import tech.bacuri.sispay.entity.Restaurante;
import tech.bacuri.sispay.entity.Usuario;
import tech.bacuri.sispay.enums.FormaPagamento;
import tech.bacuri.sispay.enums.StatusTransacao;
import tech.bacuri.sispay.validator.ExistsId;
import tech.bacuri.sispay.validator.TemCombinacaoUsuarioRestauranteFormaPagamento;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(onConstructor_ = @Deprecated)
public class NovoPagamentoOnlineForm implements TemCombinacaoUsuarioRestauranteFormaPagamento {
    @NotNull
    private FormaPagamento formaPagamento;

    @NotNull
    @ExistsId(domainClass = Restaurante.class, fieldName = "id")
    private Long idRestaurante;

    @NotNull
    @ExistsId(domainClass = Usuario.class, fieldName = "id")
    private Long idUsuario;

    @CreditCardNumber
    @NotBlank
    private String numeroCartao;

    @Min(100)
    @Max(999)
    private int codigoSeguranca;

    public Pagamento toPagamento(Long idPedido, BigDecimal valor, EntityManager manager) {
        Usuario comprador = manager.find(Usuario.class, idUsuario);
        Restaurante restaurante = manager.find(Restaurante.class, idRestaurante);

        return Pagamento.cartao(idPedido,
                valor,
                formaPagamento,
                numeroCartao,
                codigoSeguranca,
                comprador,
                restaurante,
                StatusTransacao.ESPERANDO_CONFIRMACAO_PAGAMENTO);
    }

    public boolean pagamentoOnline() {
        return this.formaPagamento.isOnline();
    }
}
