package com.bartoszthielmann.employeemanager.exception;


import org.hibernate.exception.ConstraintViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlerMvc {
    @ExceptionHandler(ConstraintViolationException.class)
    public String constraintViolationException(ConstraintViolationException ex, Model model) {
        model.addAttribute("exception", ex.getSQLException());
        return "constraintViolationException";
    }
}
