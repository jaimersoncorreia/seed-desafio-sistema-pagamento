package tech.bacuri.sispay.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;
import tech.bacuri.sispay.enums.StatusTransacao;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@Getter
@Setter
@Entity
@SequenceGenerator(name = "pagamentoSeq", sequenceName = "SQ_PAGAMENTO")
public class Pagamento {

    @Id
    @GeneratedValue(generator = "pagamentoSeq", strategy = GenerationType.SEQUENCE)
    private Long idPagamento;

    private Long idPedido;

    private BigDecimal valor;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Restaurante restaurante;

    @ElementCollection
    private Set<Transacao> transacoes = new HashSet<>();

    private String codigo;

    public Pagamento(Long idPedido, BigDecimal valor, Usuario usuario, Restaurante restaurante, StatusTransacao statusInicial) {
        this.idPedido = idPedido;
        this.valor = valor;
        this.usuario = usuario;
        this.restaurante = restaurante;
        this.transacoes.add(new Transacao(statusInicial));
        this.codigo = UUID.randomUUID().toString();
    }

    public void conclui() {
        Assert.state(!foiConcluido(), "Você não pode concluir uma compra que já foi concluída");
        this.transacoes.add(new Transacao(StatusTransacao.CONCLUIDA));
    }

    public boolean foiConcluido() {
        return this.transacoes.stream().anyMatch(Transacao::foiConcluido);
    }
}
