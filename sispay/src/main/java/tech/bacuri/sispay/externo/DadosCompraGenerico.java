package tech.bacuri.sispay.externo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.CreditCardNumber;
import tech.bacuri.sispay.entity.Pagamento;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor(onConstructor_ = @Deprecated)
public class DadosCompraGenerico {
    @CreditCardNumber
    private String num_cartao;

    @Min(100)
    @Max(999)
    private int codigo_seguranca;

    @Positive
    @NotNull
    private BigDecimal valor_compra;

    public DadosCompraGenerico(@NotNull @Valid Pagamento pagamento) {
        DadosCartao dadosCartao = pagamento.getDadosCartao();
        this.codigo_seguranca = dadosCartao.getCodigoSeguranca();
        this.num_cartao = dadosCartao.getNumero();
        this.valor_compra = pagamento.getValor();
    }
}
