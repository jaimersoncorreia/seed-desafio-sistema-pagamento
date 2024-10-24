package tech.bacuri.sispay.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import tech.bacuri.sispay.event.PedidoConfirmadoEvent;
import tech.bacuri.sispay.service.EnvioEmailService;

@RequiredArgsConstructor
@Component
public class NotificacaoClientePedidoConfirmadoListener {
    private final EnvioEmailService envioEmailService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        EnvioEmailService.Mensagem mensagem = EnvioEmailService.Mensagem.builder()
                .destinatario("jaimerson_correia+teste1@hotmail.com")
                .destinatario("jaimerson_correia+teste2@hotmail.com")
                .assunto("Envio de e-mail no Spring Boot")
                .conteudo("""
                        <strong>Caro Senhor Primeiro</strong>, <br />Venho atravez desse primeiro teste de envio de email cumprimentá-lo.
                        email confirmado %s.
                        """.formatted(event.getHello().getConfirmado()))
                .build();
        envioEmailService.enviar(mensagem);
    }

    @EventListener
    public void aoConfirmarPedidoOi(PedidoConfirmadoEvent event) {
        System.out.println("event.getHello() = " + event.getHello());
    }
}
