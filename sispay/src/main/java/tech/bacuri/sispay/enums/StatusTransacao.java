package tech.bacuri.sispay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum StatusTransacao {
    ESPERANDO_CONFIRMACAO_PAGAMENTO(1, "Aguardando confirmação de pagamento");

    private final int codigo;
    private final String descricao;

    public static StatusTransacao obter(Integer codigo) {
        return Arrays.stream(StatusTransacao.values())
                .filter(tfp -> Objects.equals(tfp.getCodigo(), codigo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Valor Ativo inválido"));
    }
}
