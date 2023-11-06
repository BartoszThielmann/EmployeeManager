package com.bartoszthielmann.employeemanager.service;

import com.bartoszthielmann.employeemanager.dao.employee.EmployeeDao;
import com.bartoszthielmann.employeemanager.entity.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService implements FieldValueExists {

    private EmployeeDao employeeDao;

    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    public Employee findById(int id) {
        return employeeDao.findById(id);
    }

    @Transactional
    public void deleteById(int id) {
        employeeDao.deleteById(id);
    }

    @Transactional
    public void save(Employee employee) {
        employeeDao.save(employee);
    }

    @Override
    public boolean fieldValueExists(String fieldName, Object value, Object ignoredId) {
        if (value == null) {
            return false;
        }
        return employeeDao.exists(fieldName, (String) value, (Integer) ignoredId);
    }
}
