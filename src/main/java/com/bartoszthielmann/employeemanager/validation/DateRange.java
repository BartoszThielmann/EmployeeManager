package com.bartoszthielmann.employeemanager.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validation annotation to validate that end date is after start date.
 *
 * Example:
 * @DateRange(startField = "startDate", endField = "endDate")
 *
 * @param startField The field containing start date
 * @param endField The field containing end date
 * @return True if end date is after start date; false otherwise
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
public @interface DateRange {

    String message() default "End date must be later than start date";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String startField();

    String endField();
}
