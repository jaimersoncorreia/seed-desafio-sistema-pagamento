package tech.bacuri.sispay.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FiltraFormasPagamentoForm {

    @NotNull
    private Long idRestaurante;

    @NotNull
    private Long idUsuario;
}
