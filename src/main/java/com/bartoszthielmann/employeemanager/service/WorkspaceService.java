package com.bartoszthielmann.employeemanager.service;

import com.bartoszthielmann.employeemanager.dao.workspace.WorkspaceDao;
import com.bartoszthielmann.employeemanager.entity.Workspace;
import com.bartoszthielmann.employeemanager.dto.WorkspaceDto;
import com.bartoszthielmann.employeemanager.mapper.WorkspaceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkspaceService {

    private WorkspaceDao workspaceDao;
    private WorkspaceMapper workspaceMapper;

    public WorkspaceService(WorkspaceDao workspaceDao, WorkspaceMapper workspaceMapper) {
        this.workspaceDao = workspaceDao;
        this.workspaceMapper = workspaceMapper;
    }

    public WorkspaceDto findById(Integer id) {
        Workspace workspace = workspaceDao.findById(id);
        return workspaceMapper.workspaceToWorkspaceDto(workspace);
    }

    public List<WorkspaceDto> findAvailableWorkspacesInOfficeBetweenDates(Integer officeId, Date start, Date end) {
        List<Workspace> workspaces = workspaceDao.findAvailableWorkspacesInOfficeBetweenDates(officeId, start, end);
        List<WorkspaceDto> workspaceDtoList = new ArrayList<>();
        workspaces.forEach(workspace -> workspaceDtoList.add(workspaceMapper.workspaceToWorkspaceDto(workspace)));
        return workspaceDtoList;
    }

    public List<WorkspaceDto> findWorkspacesByOfficeId(int id) {
        List<Workspace> workspaces = workspaceDao.findWorkspacesByOfficeId(id);
        List<WorkspaceDto> workspaceDtoList = new ArrayList<>();
        workspaces.forEach(workspace -> workspaceDtoList.add(workspaceMapper.workspaceToWorkspaceDto(workspace)));
        return workspaceDtoList;
    }

    @Transactional
    public void deleteById(int id) {
        workspaceDao.deleteById(id);
    }
}
