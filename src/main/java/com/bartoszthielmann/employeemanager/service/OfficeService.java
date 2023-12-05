package com.bartoszthielmann.employeemanager.service;

import com.bartoszthielmann.employeemanager.dao.office.OfficeDao;
import com.bartoszthielmann.employeemanager.dto.OfficeDto;
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

    public OfficeDto findById(int id) {
        Office office = officeDao.findById(id);
        return officeMapper.officeToOfficeDto(office);
    }

    public OfficeDto findOfficeByWorkspaceId(int id) {
        Office office = officeDao.findByWorkspaceId(id);
        return officeMapper.officeToOfficeDto(office);
    }

    @Transactional
    public void deleteById(int id) {
        officeDao.deleteById(id);
    }

    @Transactional
    public void save(OfficeDto officeDto) {
        Office office = officeMapper.officeDtoToOffice(officeDto);
        officeDao.save(office);
    }
}
