package com.bartoszthielmann.employeemanager.entity.employee;

import com.bartoszthielmann.employeemanager.dao.employee.EmployeeDao;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public void initialize(UniqueEmail uniqueEmail) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !employeeDao.existsByEmail(email);
    }
}
