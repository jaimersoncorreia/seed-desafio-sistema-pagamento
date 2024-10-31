package tech.bacuri.sispay.externo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(onConstructor_ = @Deprecated)
public class DadosCartao {
    private String numero;
    private int codigoSeguranca;
}
