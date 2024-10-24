package tech.bacuri.sispay.service;

import lombok.*;

import java.util.Set;

public interface EnvioEmailService {
    void enviar(Mensagem mensagem);

    @Getter
    @Builder
    @ToString
    class Mensagem {
        @Singular
        private Set<String> destinatarios;

        @NonNull
        private String assunto;

        @NonNull
        private String conteudo;
    }
}
