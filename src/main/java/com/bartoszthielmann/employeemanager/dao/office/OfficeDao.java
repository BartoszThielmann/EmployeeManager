package com.bartoszthielmann.employeemanager.dao.office;

import com.bartoszthielmann.employeemanager.entity.Office;

import java.util.List;

public interface OfficeDao {

    public List<Office> findAll();

    public Office findById(int id);

    public Office findByWorkspaceId(int id);

    public void deleteById(int id);

    public void save(Office office);
}
