package tech.bacuri.sispay.externo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor(onConstructor_ = @Deprecated)
public class DadosCompraSeyaRequest {
    @CreditCardNumber
    private String num_cartao;

    @Min(100)
    @Max(999)
    private int codigo_seguranca;

    @Positive
    private BigDecimal valor_compra;

    public DadosCompraSeyaRequest(DadosCartao dadosCartao, BigDecimal valor) {
        this.num_cartao = dadosCartao.getNumero();
        this.codigo_seguranca = dadosCartao.getCodigoSeguranca();
        this.valor_compra = valor;
    }
}
