package com.bartoszthielmann.employeemanager.service;

import com.bartoszthielmann.employeemanager.dao.office.OfficeDao;
import com.bartoszthielmann.employeemanager.entity.Workspace;
import org.springframework.stereotype.Service;
import com.bartoszthielmann.employeemanager.entity.Office;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OfficeService {

    private OfficeDao officeDao;

    public OfficeService(OfficeDao officeDao) {
        this.officeDao = officeDao;
    }

    public List<Office> findAll() {
        return officeDao.findAll();
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
