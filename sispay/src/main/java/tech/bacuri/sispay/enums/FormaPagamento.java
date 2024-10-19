package tech.bacuri.sispay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@AllArgsConstructor
@Getter
public enum FormaPagamento {
    CARTAO(1, "Cartão"),
    DINHEIRO(2, "Dinheiro"),
    MAQUINA(3, "Máquina"),
    CHEQUE(4, "Cheque"),
    OUTROS(5, "Outros");

    private final int codigo;
    private final String descricao;

    public static FormaPagamento obter(Integer codigo) {
        return Arrays.stream(FormaPagamento.values())
                .filter(tfp -> Objects.equals(tfp.getCodigo(), codigo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Valor Ativo inválido"));
    }
}
