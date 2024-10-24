package tech.bacuri.sispay.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;
import tech.bacuri.sispay.entity.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Usuario getUsuarioById(@NotNull Long idUsuario);
}
