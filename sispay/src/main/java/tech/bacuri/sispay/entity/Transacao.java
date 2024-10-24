package tech.bacuri.sispay.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.bacuri.sispay.entity.converter.StatusTransacaoConverter;
import tech.bacuri.sispay.enums.StatusTransacao;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@Getter
@Setter
@Entity
@SequenceGenerator(name = "transacaoSeq", sequenceName = "SQ_TRANSACAO")
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transacaoSeq")
    private Long id;

    @NotNull
    private Long idPedido;

    @NotNull
    private String uuid;

    @NotNull
    @Positive
    private BigDecimal valor;

    @NotNull
    @Valid
    @ManyToOne
    private Usuario usuario;

    @NotNull
    @Valid
    @ManyToOne
    private Restaurante restaurante;

    @Convert(converter = StatusTransacaoConverter.class)
    private StatusTransacao statusTransacao;

    public Transacao(Long idPedido,
                     BigDecimal valor,
                     Usuario usuario,
                     Restaurante restaurante,
                     StatusTransacao statusTransacao) {
        this.idPedido = idPedido;
        this.valor = valor;
        this.usuario = usuario;
        this.restaurante = restaurante;
        this.statusTransacao = statusTransacao;
        this.uuid = UUID.randomUUID().toString();
    }
}
