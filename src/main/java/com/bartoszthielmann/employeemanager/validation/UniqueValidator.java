package com.bartoszthielmann.employeemanager.validation;

import com.bartoszthielmann.employeemanager.service.FieldValueExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    @Autowired
    private ApplicationContext applicationContext;
    private FieldValueExists service;
    private String fieldName;
    private String primaryKeyName;

    @Override
    public void initialize(Unique unique) {
        fieldName = unique.fieldName();
        primaryKeyName = unique.primaryKeyName();
        Class<? extends FieldValueExists> service = unique.service();
        String serviceQualifier = unique.serviceQualifier();
        if (!serviceQualifier.equals("")) {
            this.service = applicationContext.getBean(serviceQualifier, service);
        } else {
            this.service = applicationContext.getBean(service);
        }
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
        Object value = beanWrapper.getPropertyValue(fieldName);
        Object ignoredId = beanWrapper.getPropertyValue(primaryKeyName);
        return !service.fieldValueExists(fieldName, value, ignoredId);
    }
}
