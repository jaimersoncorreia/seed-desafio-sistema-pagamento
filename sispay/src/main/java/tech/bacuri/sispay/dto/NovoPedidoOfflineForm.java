package tech.bacuri.sispay.dto;

import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;
import tech.bacuri.sispay.entity.Restaurante;
import tech.bacuri.sispay.entity.Transacao;
import tech.bacuri.sispay.entity.Usuario;
import tech.bacuri.sispay.enums.FormaPagamento;
import tech.bacuri.sispay.enums.StatusTransacao;
import tech.bacuri.sispay.validator.ExistsId;

import java.math.BigDecimal;

@Getter
@Setter
public class NovoPedidoOfflineForm {

    @NotNull
    private FormaPagamento formaPagamento;

    @NotNull
    @ExistsId(domainClass = Restaurante.class, fieldName = "id")
    private Long idRestaurante;

    @NotNull
    @ExistsId(domainClass = Usuario.class, fieldName = "id")
    private Long idUsuario;

    public boolean isOffline() {
        return !formaPagamento.isOnline();
    }

    @Transactional
    public Transacao toTransacao(Long idPedido,
                                 BigDecimal valor,
                                 EntityManager manager) {
        Usuario usuario = manager.find(Usuario.class, this.idUsuario);
        Restaurante restaurante = manager.find(Restaurante.class, this.idRestaurante);
        return new Transacao(idPedido, valor, usuario, restaurante, StatusTransacao.ESPERANDO_CONFIRMACAO_PAGAMENTO);
    }
}
