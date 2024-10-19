package tech.bacuri.sispay.repository;

import org.springframework.data.repository.CrudRepository;
import tech.bacuri.sispay.entity.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
}
