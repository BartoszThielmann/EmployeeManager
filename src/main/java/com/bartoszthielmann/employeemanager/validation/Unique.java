package com.bartoszthielmann.employeemanager.validation;

import com.bartoszthielmann.employeemanager.service.FieldValueExists;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import java.lang.annotation.ElementType;


@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
public @interface Unique {

    String message() default "Value must be unique";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
    Class<? extends FieldValueExists> service();

    /**
     * If there are multiple classes of type given as service, this method can be used to point to a specific bean
     * which will be autowired into the custom validator
     */
    String serviceQualifier() default "";

    String fieldName();
}
