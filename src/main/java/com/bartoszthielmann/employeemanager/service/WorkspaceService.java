package com.bartoszthielmann.employeemanager.service;

import com.bartoszthielmann.employeemanager.dao.workspace.WorkspaceDao;
import com.bartoszthielmann.employeemanager.entity.Workspace;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
public class WorkspaceService {

    private WorkspaceDao workspaceDao;

    public WorkspaceService(WorkspaceDao workspaceDao) {
        this.workspaceDao = workspaceDao;
    }

    public Workspace findById(Integer id) {
        return workspaceDao.findById(id);
    }

    public List<Workspace> findAvailableWorkspacesInOfficeBetweenDates(Integer officeId, Date start, Date end) {
        return workspaceDao.findAvailableWorkspacesInOfficeBetweenDates(officeId, start, end);
    }

    public List<Workspace> findWorkspacesByOfficeId(int id) {
        return workspaceDao.findWorkspacesByOfficeId(id);
    }

    @Transactional
    public void deleteById(int id) {
        workspaceDao.deleteById(id);
    }
}
