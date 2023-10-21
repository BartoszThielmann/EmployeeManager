package com.bartoszthielmann.employeemanager.service;

import com.bartoszthielmann.employeemanager.dao.EmployeeDao;
import com.bartoszthielmann.employeemanager.entity.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

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
}
