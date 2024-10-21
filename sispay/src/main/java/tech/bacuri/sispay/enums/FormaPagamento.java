package tech.bacuri.sispay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@AllArgsConstructor
@Getter
public enum FormaPagamento {
    VISA(1, true, "Cartão visa"),
    MASTER(2, true, "Cartão master"),
    ELO(3, true, "Cartão elo"),
    HIPERCARD(4, true, "Cartão hiper"),
    MAQUINETA(5, false, "Maquina para passar cartão"),
    DINHEIRO(6, false, "Dinheiro para pagar o pedido"),
    OUTROS(7, true, "Outros");

    private final int codigo;
    private final boolean online;
    private final String descricao;

    public static FormaPagamento obter(Integer codigo) {
        return Arrays.stream(FormaPagamento.values())
                .filter(tfp -> Objects.equals(tfp.getCodigo(), codigo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Valor Ativo inválido"));
    }
}
