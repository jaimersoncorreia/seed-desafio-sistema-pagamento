package tech.bacuri.sispay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.bacuri.sispay.entity.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
}
