package tech.bacuri.sispay.externo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.CreditCardNumber;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@AllArgsConstructor
@Getter
@ToString
public class DadosCartaoSeyaRequest {
    @CreditCardNumber
    private String num_cartao;
    @Min(100)
    @Max(999)
    private int codigo_seguranca;

    public DadosCartaoSeyaRequest(DadosCartao dadosCartao) {
        this.num_cartao = dadosCartao.getNumero();
        this.codigo_seguranca = dadosCartao.getCodigoSeguranca();
    }
}
