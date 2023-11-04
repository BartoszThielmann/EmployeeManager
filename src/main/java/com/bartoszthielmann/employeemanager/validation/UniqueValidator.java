package com.bartoszthielmann.employeemanager.validation;

import com.bartoszthielmann.employeemanager.service.FieldValueExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class UniqueValidator implements ConstraintValidator<Unique, String> {

    @Autowired
    private ApplicationContext applicationContext;

    private FieldValueExists service;
    private String fieldName;
    @Override
    public void initialize(Unique unique) {
        fieldName = unique.fieldName();
        Class<? extends FieldValueExists> service = unique.service();
        String serviceQualifier = unique.serviceQualifier();
        if (!serviceQualifier.equals("")) {
            this.service = applicationContext.getBean(serviceQualifier, service);
        } else {
            this.service = applicationContext.getBean(service);
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return !service.fieldValueExists(fieldName, value);
    }
}
