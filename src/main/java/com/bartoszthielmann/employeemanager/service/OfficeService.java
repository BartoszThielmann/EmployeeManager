package com.bartoszthielmann.employeemanager.service;

import com.bartoszthielmann.employeemanager.dao.office.OfficeDao;
import com.bartoszthielmann.employeemanager.dto.OfficeDto;
import com.bartoszthielmann.employeemanager.entity.Workspace;
import com.bartoszthielmann.employeemanager.mapper.OfficeMapper;
import org.springframework.stereotype.Service;
import com.bartoszthielmann.employeemanager.entity.Office;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OfficeService {

    private OfficeDao officeDao;
    private OfficeMapper officeMapper;

    public OfficeService(OfficeDao officeDao, OfficeMapper officeMapper) {
        this.officeDao = officeDao;
        this.officeMapper = officeMapper;
    }

    public List<OfficeDto> findAll() {
        List<Office> offices = officeDao.findAll();
        List<OfficeDto> officeDtos = new ArrayList<>();
        offices.forEach(office -> officeDtos.add(officeMapper.officeToOfficeDto(office)));
        return officeDtos;
    }

    public Office findById(int id) {
        return officeDao.findById(id);
    }

    @Transactional
    public void deleteById(int id) {
        officeDao.deleteById(id);
    }

    @Transactional
    public void save(Office office) {
        List<Workspace> workspacesList = office.getWorkspaces();
        if (workspacesList != null) {
            for (Workspace workspace : workspacesList) {
                workspace.setOffice(office);
            }
        }
        officeDao.save(office);
    }
}
