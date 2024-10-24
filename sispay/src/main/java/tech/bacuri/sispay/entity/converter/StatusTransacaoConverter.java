package tech.bacuri.sispay.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tech.bacuri.sispay.enums.StatusTransacao;

import java.util.Objects;

@Converter
public class StatusTransacaoConverter implements AttributeConverter<StatusTransacao, Integer> {
    @Override
    public Integer convertToDatabaseColumn(StatusTransacao status) {
        if (Objects.isNull(status)) return null;
        return status.getCodigo();
    }

    @Override
    public StatusTransacao convertToEntityAttribute(Integer codigo) {
        if (Objects.isNull(codigo)) return null;
        return StatusTransacao.obter(codigo);
    }
}
