package tech.bacuri.sispay.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.bacuri.sispay.dto.NovoUsuarioForm;
import tech.bacuri.sispay.entity.Usuario;
import tech.bacuri.sispay.repository.UsuarioRepository;

@AllArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid NovoUsuarioForm form) {
        Usuario novoUsuario = form.toUsuario();
        Usuario usuario = usuarioRepository.save(novoUsuario);
        return ResponseEntity.ok(usuario);
    }
}
