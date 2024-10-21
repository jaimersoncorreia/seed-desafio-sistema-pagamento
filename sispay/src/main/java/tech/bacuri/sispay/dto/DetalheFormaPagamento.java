package tech.bacuri.sispay.dto;

import lombok.Getter;
import lombok.Setter;
import tech.bacuri.sispay.enums.FormaPagamento;

@Getter
@Setter
public class DetalheFormaPagamento {
    private String descricao;
    private String id;

    public DetalheFormaPagamento(FormaPagamento formaPagamento) {
        this.id = formaPagamento.toString();
        this.descricao = formaPagamento.getDescricao();
    }
}
