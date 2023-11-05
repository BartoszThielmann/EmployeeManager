package com.bartoszthielmann.employeemanager.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.util.Date;

public class DateRangeValidator implements ConstraintValidator<DateRange, Object> {

    private String startField;
    private String endField;

    @Override
    public void initialize(DateRange dateRange) {
        startField = dateRange.startField();
        endField = dateRange.endField();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
        Date startDate = (Date) beanWrapper.getPropertyValue(startField);
        Date endDate = (Date) beanWrapper.getPropertyValue(endField);
        if (startDate == null || endDate == null) {
            return false;
        }
        return endDate.after(startDate);
    }
}
