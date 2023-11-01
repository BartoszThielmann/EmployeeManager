package com.bartoszthielmann.employeemanager.dao.employee;

import com.bartoszthielmann.employeemanager.entity.employee.Employee;

import java.util.List;

public interface EmployeeDao {


    public List<Employee> findAll();

    public Employee findById(int id);

    public void deleteById(int id);

    public void save(Employee employee);

    public boolean existsByEmail(String email);
}
