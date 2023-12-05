package com.bartoszthielmann.employeemanager.mapper;

import com.bartoszthielmann.employeemanager.dto.WorkspaceDto;
import com.bartoszthielmann.employeemanager.entity.Workspace;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface WorkspaceMapper {
    WorkspaceDto workspaceToWorkspaceDto(Workspace workspace);
    Workspace workspaceDtoToWorkspace(WorkspaceDto workspaceDto);
}
