package tech.bacuri.sispay;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import tech.bacuri.sispay.entity.Restaurante;
import tech.bacuri.sispay.entity.Usuario;
import tech.bacuri.sispay.enums.FormaPagamento;
import tech.bacuri.sispay.repository.RestauranteRepository;
import tech.bacuri.sispay.repository.UsuarioRepository;

@AllArgsConstructor
@SpringBootApplication
public class SispayApplication implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final RestauranteRepository restauranteRepository;

    public static void main(String[] args) {
        SpringApplication.run(SispayApplication.class, args);
    }

    @Transactional
    @Override
    public void run(String... args) {
        usuarioRepository.save(new Usuario("teste1@bacuri.tech", FormaPagamento.VISA, FormaPagamento.ELO, FormaPagamento.HIPERCARD, FormaPagamento.DINHEIRO));
        usuarioRepository.save(new Usuario("teste2@bacuri.tech", FormaPagamento.MASTER, FormaPagamento.ELO));
        usuarioRepository.save(new Usuario("teste3@bacuri.tech", FormaPagamento.VISA, FormaPagamento.HIPERCARD));
        usuarioRepository.save(new Usuario("teste4@bacuri.tech", FormaPagamento.OUTROS, FormaPagamento.ELO, FormaPagamento.VISA));

        restauranteRepository.save(new Restaurante("bacuri tech 1", FormaPagamento.VISA, FormaPagamento.ELO, FormaPagamento.DINHEIRO));
        restauranteRepository.save(new Restaurante("bacuri tech 2", FormaPagamento.HIPERCARD, FormaPagamento.ELO));
        restauranteRepository.save(new Restaurante("bacuri tech 3", FormaPagamento.MASTER, FormaPagamento.ELO));
        restauranteRepository.save(new Restaurante("bacuri tech 4", FormaPagamento.VISA, FormaPagamento.OUTROS));

    }
}
