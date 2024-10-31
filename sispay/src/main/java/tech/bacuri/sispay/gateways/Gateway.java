package tech.bacuri.sispay.gateways;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;
import tech.bacuri.sispay.entity.Pagamento;
import tech.bacuri.sispay.entity.Resultado;
import tech.bacuri.sispay.entity.Transacao;

import java.math.BigDecimal;

public abstract class Gateway {
    public boolean aceita(@NotNull @Valid Pagamento pagamento) {
        Assert.isTrue(!pagamento.foiConcluido(), "Por algum motivo um motivo já concluído está tentando ser processado de novo");
        return aceiteEspecifico(pagamento);
    }

    protected abstract boolean aceiteEspecifico(
            @NotNull @Valid Pagamento pagamento);

    public Resultado<Exception, Transacao> processa(@NotNull @Valid Pagamento pagamento) {
        this.aceita(pagamento);
        return processaEspecifico(pagamento);
    }

    protected abstract Resultado<Exception, Transacao> processaEspecifico(@NotNull @Valid Pagamento pagamento);

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    public BigDecimal custo(@NotNull @Valid Pagamento pagamento) {
        this.aceita(pagamento);
        return custoEspecifico(pagamento);
    }

    protected abstract BigDecimal custoEspecifico(@NotNull @Valid Pagamento pagamento);
}
