package tech.bacuri.sispay.validator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.Assert;

import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {
    private String fieldName;
    private Class<?> domainClass;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(UniqueValue params) {
        fieldName = params.fieldName();
        domainClass = params.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        List<?> list = manager
                .createQuery("select 1 from " + domainClass.getName() + " where " + fieldName + "=:value")
                .setParameter("value", value)
                .getResultList();

        Assert.isTrue(list.size() <= 1, "foi encontrado mais de um " + domainClass + " com o atributo " + fieldName + " = " + value);

        return list.isEmpty();
    }
}
