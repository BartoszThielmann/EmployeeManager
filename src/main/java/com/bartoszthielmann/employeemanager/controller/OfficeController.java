package com.bartoszthielmann.employeemanager.controller;

import com.bartoszthielmann.employeemanager.dto.OfficeDto;
import com.bartoszthielmann.employeemanager.dto.WorkspaceDto;
import com.bartoszthielmann.employeemanager.service.OfficeService;
import com.bartoszthielmann.employeemanager.service.WorkspaceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/offices")
public class OfficeController {

    private OfficeService officeService;
    private WorkspaceService workspaceService;

    public OfficeController(OfficeService officeService, WorkspaceService workspaceService) {
        this.officeService = officeService;
        this.workspaceService = workspaceService;
    }

    @GetMapping("/create")
    public String createOffice(Model model) {
        model.addAttribute("office", new OfficeDto());
        return "office-form";
    }

    @GetMapping("/list")
    public String showOffices(Model model) {
        List<OfficeDto> offices = officeService.findAll();
        model.addAttribute("offices", offices);

        return "office-list";
    }

    @GetMapping("/update")
    public String updateOffice(@RequestParam("id") int id, Model model) {
        OfficeDto officeDto = officeService.findById(id);
        model.addAttribute("office", officeDto);
        return "office-form";
    }

    @PostMapping("/save")
    public String saveOffice(@Valid @ModelAttribute("office") OfficeDto officeDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "office-form";
        }
        officeService.save(officeDto);
        return "redirect:list";
    }

    @RequestMapping(value = "/save", params = {"addWorkspace"})
    public String addWorkspace(@ModelAttribute("office") OfficeDto officeDto) {
        officeDto.addWorkspace(new WorkspaceDto());
        return "/office-form";
    }

    @RequestMapping(value = "/save", params = {"removeWorkspace"})
    public String removeWorkspace(@ModelAttribute("office") OfficeDto officeDto, HttpServletRequest req) {
        int workspaceIndex = Integer.parseInt(req.getParameter("removeWorkspace"));
        officeDto.getWorkspaces().remove(workspaceIndex);
        return "/office-form";
    }

    @GetMapping("/delete")
    public String deleteOffice(@RequestParam("id") int id) {
        officeService.deleteById(id);
        return "redirect:list";
    }
}
