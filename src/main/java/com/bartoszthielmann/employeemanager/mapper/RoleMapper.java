package com.bartoszthielmann.employeemanager.mapper;

import com.bartoszthielmann.employeemanager.dto.RoleDto;
import com.bartoszthielmann.employeemanager.entity.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {

    RoleDto roleToRoleDto(Role role);
    Role roleDtoToRole(RoleDto roleDto);
}
