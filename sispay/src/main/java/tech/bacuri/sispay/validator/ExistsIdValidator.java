package tech.bacuri.sispay.validator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

public class ExistsIdValidator implements ConstraintValidator<ExistsId, Object> {
    private String fieldName;
    private Class<?> domainClass;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(ExistsId params) {
        fieldName = params.fieldName();
        domainClass = params.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) return true;

        List<?> list = manager
                .createQuery("select 1 from " + domainClass.getName() + " where " + fieldName + "=:value")
                .setParameter("value", value)
                .getResultList();

        Assert.isTrue(list.size() <= 1, "aconteceu algo bizarro e você tem mais de um " + domainClass + " com o atributo " + fieldName + " com o valor = " + value);

        return !list.isEmpty();
    }
}
