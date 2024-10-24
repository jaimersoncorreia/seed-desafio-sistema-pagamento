package tech.bacuri.sispay.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.AbstractAggregateRoot;
import tech.bacuri.sispay.event.PedidoConfirmadoEvent;

@Getter
@ToString
@Entity
@SequenceGenerator(name = "helloSeq", sequenceName = "SQ_HELLO")
public class Hello extends AbstractAggregateRoot<Hello> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "helloSeq")
    private Long id;
    private Boolean confirmado = false;

    public void confirmar() {
        this.confirmado = true;
        registerEvent(new PedidoConfirmadoEvent(this));
    }
}
