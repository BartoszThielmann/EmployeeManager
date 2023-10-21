package com.bartoszthielmann.employeemanager.dao;

import com.bartoszthielmann.employeemanager.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface EmployeeDao {


    public List<Employee> findAll();

    public Employee findById(int id);

    public void deleteById(int id);

    public void save(Employee employee);
}
