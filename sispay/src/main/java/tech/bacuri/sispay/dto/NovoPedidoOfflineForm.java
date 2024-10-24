package tech.bacuri.sispay.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import tech.bacuri.sispay.entity.Restaurante;
import tech.bacuri.sispay.entity.Usuario;
import tech.bacuri.sispay.enums.FormaPagamento;
import tech.bacuri.sispay.validator.ExistsId;

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
}
