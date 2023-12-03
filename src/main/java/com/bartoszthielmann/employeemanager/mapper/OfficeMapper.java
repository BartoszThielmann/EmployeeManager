package com.bartoszthielmann.employeemanager.mapper;

import com.bartoszthielmann.employeemanager.dto.OfficeDto;
import com.bartoszthielmann.employeemanager.entity.Office;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(uses = WorkspaceMapper.class, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface OfficeMapper {

    OfficeDto officeToOfficeDto(Office office);
    Office officeDtoToOffice(OfficeDto officeDto);
}
