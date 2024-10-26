package tech.bacuri.sispay.entity;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.bacuri.sispay.entity.converter.StatusTransacaoConverter;
import tech.bacuri.sispay.enums.StatusTransacao;

import java.util.UUID;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@Getter
@Setter
@Embeddable
public class Transacao {
    @Convert(converter = StatusTransacaoConverter.class)
    private StatusTransacao statusTransacao;

    @NotNull
    private String codigo;

    public Transacao(StatusTransacao statusTransacao) {
        this.statusTransacao = statusTransacao;
        this.codigo = UUID.randomUUID().toString();
    }
}
