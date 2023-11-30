package com.bartoszthielmann.employeemanager.dao.workspace;

import com.bartoszthielmann.employeemanager.entity.Workspace;

import java.sql.Date;
import java.util.List;

public interface WorkspaceDao {

    public List<Workspace> findAll();

    public Workspace findById(int id);

    public List<Workspace> findAvailableWorkspacesInOfficeBetweenDates(Integer officeId, Date start, Date end);

    public void deleteById(int id);

    public void save(Workspace workspace);

    public List<Workspace> findWorkspacesByOfficeId(int id);
}
