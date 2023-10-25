package com.bartoszthielmann.employeemanager.controller;

import com.bartoszthielmann.employeemanager.entity.Office;
import com.bartoszthielmann.employeemanager.service.OfficeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/offices")
public class OfficeController {

    private OfficeService officeService;

    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @GetMapping("list")
    public String showOffices(Model model) {
        List<Office> offices = officeService.findAll();
        model.addAttribute("offices", offices);

        return "office-list";
    }
}
