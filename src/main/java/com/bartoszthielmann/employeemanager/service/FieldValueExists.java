package com.bartoszthielmann.employeemanager.service;


public interface FieldValueExists {

    /**
     * Checks whether a given value exists for a given field
     *
     * @param fieldName The field name to check for
     * @param value The value to check for
     * @param ignoredId Ignore row with given id. Allows to avoid returning
     *                  true when updating Entity.
     * @return True if the value exists for the field; false otherwise
     */
    public boolean fieldValueExists(String fieldName, Object value, Object ignoredId);
}
