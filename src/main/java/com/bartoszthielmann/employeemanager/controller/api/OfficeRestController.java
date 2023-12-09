package com.bartoszthielmann.employeemanager.controller.api;

import com.bartoszthielmann.employeemanager.dto.OfficeDto;
import com.bartoszthielmann.employeemanager.service.OfficeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/offices")
public class OfficeRestController {

    private OfficeService officeService;

    public OfficeRestController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @GetMapping
    public ResponseEntity<List<OfficeDto>> getAllOffices() {
        List<OfficeDto> offices = officeService.findAll();
        return new ResponseEntity<>(offices, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfficeDto> getOfficeById(@PathVariable Integer id) {
        OfficeDto office = officeService.findById(id);
        if (office != null) {
            return new ResponseEntity<>(office, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public void saveOffice(@RequestBody OfficeDto office) {
        officeService.save(office);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Integer id) {
        officeService.deleteById(id);
    }
}
