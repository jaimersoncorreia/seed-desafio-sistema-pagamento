package tech.bacuri.sispay.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tech.bacuri.sispay.enums.FormaPagamento;

import java.util.Objects;

@Converter
public class TipoFormaPagamentoConverter implements AttributeConverter<FormaPagamento, Integer> {
    @Override
    public Integer convertToDatabaseColumn(FormaPagamento tipo) {
        if (Objects.isNull(tipo)) return null;
        return tipo.getCodigo();
    }

    @Override
    public FormaPagamento convertToEntityAttribute(Integer codigo) {
        if (Objects.isNull(codigo)) return null;
        return FormaPagamento.obter(codigo);
    }

}
