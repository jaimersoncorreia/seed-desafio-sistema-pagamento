package tech.bacuri.sispay.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import tech.bacuri.sispay.entity.Usuario;
import tech.bacuri.sispay.enums.FormaPagamento;

@Getter
@Setter
public class NovoUsuarioForm {

    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private FormaPagamento[] formasPagamento;

    public Usuario toUsuario() {
        return new Usuario(this.email, formasPagamento);
    }
}
