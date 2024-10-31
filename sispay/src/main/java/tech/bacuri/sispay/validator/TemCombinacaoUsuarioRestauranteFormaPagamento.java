package tech.bacuri.sispay.validator;

import tech.bacuri.sispay.enums.FormaPagamento;

public interface TemCombinacaoUsuarioRestauranteFormaPagamento {
    Long getIdRestaurante();

    Long getIdUsuario();

    FormaPagamento getFormaPagamento();
}
