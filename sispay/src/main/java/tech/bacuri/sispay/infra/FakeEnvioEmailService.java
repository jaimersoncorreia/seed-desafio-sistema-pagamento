package tech.bacuri.sispay.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import tech.bacuri.sispay.service.EnvioEmailService;

@Profile("fake")
@RequiredArgsConstructor
@Service
public class FakeEnvioEmailService implements EnvioEmailService {

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void enviar(Mensagem mensagem){
        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Enviano email fake ...");
        System.out.println("de = " + this.from);
        System.out.println("para = " + mensagem.getDestinatarios());
        System.out.println("assunto = " + mensagem.getAssunto());
        System.out.println("conteudo = " + mensagem.getConteudo());
    }
}
