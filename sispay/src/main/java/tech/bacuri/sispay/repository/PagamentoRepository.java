package tech.bacuri.sispay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.bacuri.sispay.entity.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    Pagamento getPagamentoByIdPedido(Long idPedido);
}
