package com.bartoszthielmann.employeemanager.service;

import com.bartoszthielmann.employeemanager.dao.office.OfficeDao;
import org.springframework.stereotype.Service;
import com.bartoszthielmann.employeemanager.entity.Office;
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
}
