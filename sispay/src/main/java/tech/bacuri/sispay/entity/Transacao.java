package tech.bacuri.sispay.entity;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.bacuri.sispay.entity.converter.StatusTransacaoConverter;
import tech.bacuri.sispay.enums.StatusTransacao;
import tech.bacuri.sispay.gateways.Gateway;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
public class Transacao {
    @Convert(converter = StatusTransacaoConverter.class)
    private StatusTransacao statusTransacao;

    @EqualsAndHashCode.Include
    @NotNull
    private String codigo;

    @NotNull
    @PastOrPresent
    private LocalDateTime instante;
    private String informacaoAdicional;

    public Transacao(StatusTransacao statusTransacao) {
        this.statusTransacao = statusTransacao;
        this.codigo = UUID.randomUUID().toString();
        this.instante = LocalDateTime.now();
    }

    public static Transacao concluida(Gateway gateway) {
        Transacao transacao = new Transacao(StatusTransacao.CONCLUIDA);
        transacao.informacaoAdicional = FacilitadorJackson.serializa(Map.of("gateway", gateway.toString()));
        return transacao;
    }

    public boolean foiConcluido() {
        return Objects.equals(this.statusTransacao, StatusTransacao.CONCLUIDA);
    }

    public void setInfoAdicional(Map<String, Object> infoAdicional) {
        this.informacaoAdicional = FacilitadorJackson.serializa(infoAdicional);
    }
}
