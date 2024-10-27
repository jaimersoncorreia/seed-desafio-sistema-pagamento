package tech.bacuri.sispay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.bacuri.sispay.entity.Pagamento;

import java.util.Optional;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    Pagamento getPagamentoByIdPedido(Long idPedido);

    Optional<Pagamento> findByCodigo(String codigoPagamento);
}
