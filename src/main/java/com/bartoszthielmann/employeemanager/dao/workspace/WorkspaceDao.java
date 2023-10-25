package com.bartoszthielmann.employeemanager.dao.workspace;

import com.bartoszthielmann.employeemanager.entity.Workspace;

import java.util.List;

public interface WorkspaceDao {

    public List<Workspace> findAll();

    public Workspace findById(int id);

    public void deleteById(int id);

    public void save(Workspace workspace);
}
