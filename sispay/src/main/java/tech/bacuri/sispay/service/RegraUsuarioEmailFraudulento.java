package tech.bacuri.sispay.service;

import org.springframework.stereotype.Service;
import tech.bacuri.sispay.entity.Usuario;
import tech.bacuri.sispay.enums.FormaPagamento;

import java.util.Set;

@Service
public class RegraUsuarioEmailFraudulento implements RegraFraude {
    private final Set<String> emailsBloquados = Set.of("teste1@bacuri.tech");

    @Override
    public boolean aceita(FormaPagamento formaPagamento, Usuario usuario) {
        return !formaPagamento.isOnline() || !emailsBloquados.contains(usuario.getEmail());
    }
}
