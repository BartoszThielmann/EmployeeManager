package com.bartoszthielmann.employeemanager.service;

import jakarta.validation.constraints.NotNull;

public interface FieldValueExists {
    /**
     * Checks whether a given value exists for a given field
     *
     * @param fieldName The field name to check for
     * @param value The value to check for
     * @return True if the value exists for the field; false otherwise
     */
    public boolean fieldValueExists(@NotNull String fieldName, Object value);
}
