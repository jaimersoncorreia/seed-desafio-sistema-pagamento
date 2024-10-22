package tech.bacuri.sispay.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.bacuri.sispay.dto.DetalheFormaPagamento;
import tech.bacuri.sispay.dto.FiltraFormasPagamentoForm;
import tech.bacuri.sispay.entity.Restaurante;
import tech.bacuri.sispay.entity.Usuario;
import tech.bacuri.sispay.enums.FormaPagamento;
import tech.bacuri.sispay.repository.RestauranteRepository;
import tech.bacuri.sispay.repository.UsuarioRepository;
import tech.bacuri.sispay.service.RegraUsuarioEmailFraudulento;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pagamentos")
public class ListaPagamentoController {

    private final UsuarioRepository usuarioRepository;
    private final RestauranteRepository restauranteRepository;
    private final RegraUsuarioEmailFraudulento regraUsuarioEmailFraudulento;

    @Transactional
    @GetMapping
    public ResponseEntity<List<DetalheFormaPagamento>> lista(@Valid FiltraFormasPagamentoForm form) {
        Usuario usuario = usuarioRepository.findById(form.getIdUsuario()).orElseThrow(IllegalArgumentException::new);
        Restaurante restaurante = restauranteRepository.findById(form.getIdRestaurante()).orElseThrow(IllegalArgumentException::new);
        Set<FormaPagamento> formaPagamentos = usuario.filtraFormasPagamento(restaurante, regraUsuarioEmailFraudulento);
        return ResponseEntity.ok(formaPagamentos.stream().map(DetalheFormaPagamento::new).toList());
    }
}
