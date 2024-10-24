package tech.bacuri.sispay.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FiltraFormasPagamentoForm {

    @NotNull
    private Long idRestaurante;

    @NotNull
    private Long idUsuario;
}
