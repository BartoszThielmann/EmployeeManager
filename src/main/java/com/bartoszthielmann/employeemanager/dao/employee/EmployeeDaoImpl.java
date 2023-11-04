package com.bartoszthielmann.employeemanager.dao.employee;

import com.bartoszthielmann.employeemanager.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    private EntityManager entityManager;

    public EmployeeDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findAll() {
        TypedQuery<Employee> query = entityManager.createQuery("FROM Employee", Employee.class);
        return query.getResultList();
    }

    @Override
    public Employee findById(int id) {
        return entityManager.find(Employee.class, id);
    }

    @Override
    public void deleteById(int id) {
        Employee employee = entityManager.find(Employee.class, id);
        if (employee != null) {
            entityManager.remove(employee);
        }
    }

    @Override
    public void save(Employee employee) {
        entityManager.merge(employee);
    }

    @Override
    public boolean exists(String fieldName, String value) {
        // Workaround to have named parameter concatenated to a variable
        String queryString = "select e from Employee e where e." + fieldName + " = :value";
        TypedQuery<Employee> query = entityManager.createQuery(queryString, Employee.class);
        query.setParameter("value", value);
        try {
            query.getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }
}
