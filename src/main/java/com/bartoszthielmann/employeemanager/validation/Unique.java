package com.bartoszthielmann.employeemanager.validation;

import com.bartoszthielmann.employeemanager.service.FieldValueExists;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Validation annotation to validate that a field is unique
 * i.e. it doesn't exist in a data source
 *
 * Example:
 * @Unique(message = "Already exists", service = EmployeeService.class,
 *         fieldName = "email", primaryKeyName = "id")
 *
 * @param service Service used to perform operations on data layer
 * @param serviceQualifier If there are multiple classes of type given as
 *                         service, this method can be used to point to a
 *                         specific bean which will be autowired into the
 *                         custom validator
 * @param fieldName Field which is supposed to be validated for uniqueness
 * @param primaryKeyName Name of the primary key of an Entity which is
 *                       validated
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
public @interface Unique {

    String message() default "Value must be unique";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
    Class<? extends FieldValueExists> service();

    String serviceQualifier() default "";

    String fieldName();

    String primaryKeyName();
}
