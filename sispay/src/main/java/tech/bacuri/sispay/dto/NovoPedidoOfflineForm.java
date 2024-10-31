package tech.bacuri.sispay.dto;

import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;
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
public class NovoPedidoOfflineForm implements TemCombinacaoUsuarioRestauranteFormaPagamento {

    @NotNull
    private FormaPagamento formaPagamento;

    @NotNull
    @ExistsId(domainClass = Restaurante.class, fieldName = "id")
    private Long idRestaurante;

    @NotNull
    @ExistsId(domainClass = Usuario.class, fieldName = "id")
    private Long idUsuario;

    public NovoPedidoOfflineForm(FormaPagamento formaPagamento, Long idRestaurante, Long idUsuario) {
        this.formaPagamento = formaPagamento;
        this.idRestaurante = idRestaurante;
        this.idUsuario = idUsuario;
    }

    public boolean isOffline() {
        return !formaPagamento.isOnline();
    }

    @Transactional
    public Pagamento toPagamento(Long idPedido, BigDecimal valor, EntityManager manager) {
        Usuario usuario = manager.find(Usuario.class, this.idUsuario);
        Restaurante restaurante = manager.find(Restaurante.class, this.idRestaurante);

        return Pagamento.offline(idPedido, valor, formaPagamento, usuario, restaurante, StatusTransacao.ESPERANDO_CONFIRMACAO_PAGAMENTO);
    }
}
