package com.bartoszthielmann.employeemanager.service;

import com.bartoszthielmann.employeemanager.dao.workspace.WorkspaceDao;
import com.bartoszthielmann.employeemanager.entity.Workspace;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkspaceService {

    private WorkspaceDao workspaceDao;

    public WorkspaceService(WorkspaceDao workspaceDao) {
        this.workspaceDao = workspaceDao;
    }


    public List<Workspace> findWorkspacesByOfficeId(int id) {
        return workspaceDao.findWorkspacesByOfficeId(id);
    }
}
