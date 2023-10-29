package com.bartoszthielmann.employeemanager.controller;

import com.bartoszthielmann.employeemanager.entity.Office;
import com.bartoszthielmann.employeemanager.entity.Workspace;
import com.bartoszthielmann.employeemanager.service.OfficeService;
import com.bartoszthielmann.employeemanager.service.WorkspaceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.addAttribute("office", new Office());
        return "office-form";
    }

    @GetMapping("/list")
    public String showOffices(Model model) {
        List<Office> offices = officeService.findAll();
        model.addAttribute("offices", offices);

        return "office-list";
    }

    @GetMapping("/update")
    public String updateOffice(@RequestParam("id") int id, Model model) {
        model.addAttribute("office", officeService.findById(id));
        return "office-form";
    }

    @PostMapping("/save")
    public String saveOffice(@ModelAttribute Office office) {
        List<Workspace> workspacesList = office.getWorkspaces();
        if (workspacesList != null) {
            for (Workspace workspace : workspacesList) {
                workspace.setOffice(office);
            }
        }
        officeService.save(office);
        return "redirect:list";
    }

    @RequestMapping(value = "/save", params = {"addWorkspace"})
    public String addWorkspace(@ModelAttribute Office office) {
        office.addWorkspace();
        return "/office-form";
    }

    @RequestMapping(value = "/save", params = {"removeWorkspace"})
    public String removeWorkspace(@ModelAttribute Office office, HttpServletRequest req) {
        // When "remove" is clicked this immediately removes Workspace from database
        // This does not work ideally and will have to be refactored
        workspaceService.deleteById(office.getWorkspaces()
                .get(Integer.parseInt(req.getParameter("removeWorkspace"))).getId());
        office.getWorkspaces().remove(Integer.parseInt(req.getParameter("removeWorkspace")));
        return "/office-form";
    }

    @GetMapping("/delete")
    public String deleteOffice(@RequestParam("id") int id) {
        officeService.deleteById(id);
        return "redirect:list";
    }
}
