package tech.bacuri.sispay.service;

import tech.bacuri.sispay.entity.Usuario;
import tech.bacuri.sispay.enums.FormaPagamento;

public interface RegraFraude {
    boolean aceita(FormaPagamento formaPagamento, Usuario usuario);
}
