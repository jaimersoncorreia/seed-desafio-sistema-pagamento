package tech.bacuri.sispay.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import tech.bacuri.sispay.entity.Hello;

@Getter
@AllArgsConstructor
@ToString
public class PedidoConfirmadoEvent {
    private Hello hello;
}
