package com.bartoszthielmann.employeemanager.entity.employee;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import java.lang.annotation.ElementType;


@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
@interface UniqueEmail {

    String message() default "Email must be unique";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
