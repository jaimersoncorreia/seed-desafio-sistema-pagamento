package tech.bacuri.sispay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.bacuri.sispay.entity.Hello;

public interface HelloRepository extends JpaRepository<Hello, Long> {
}
