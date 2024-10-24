package tech.bacuri.sispay.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.bacuri.sispay.entity.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    Restaurante getRestaurantesById(@NotNull Long idRestaurante);
}
