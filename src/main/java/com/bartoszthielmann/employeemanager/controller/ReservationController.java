package com.bartoszthielmann.employeemanager.controller;

import com.bartoszthielmann.employeemanager.entity.Employee;
import com.bartoszthielmann.employeemanager.entity.Reservation;
import com.bartoszthielmann.employeemanager.entity.ReservationForm;
import com.bartoszthielmann.employeemanager.entity.Workspace;
import com.bartoszthielmann.employeemanager.service.EmployeeService;
import com.bartoszthielmann.employeemanager.service.ReservationService;
import com.bartoszthielmann.employeemanager.service.WorkspaceService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private ReservationService reservationService;
    private WorkspaceService workspaceService;
    private EmployeeService employeeService;

    public ReservationController(ReservationService reservationService, WorkspaceService workspaceService,
                                 EmployeeService employeeService) {
        this.reservationService = reservationService;
        this.workspaceService = workspaceService;
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public String showReservations(Model model) {
        List<Reservation> reservations = reservationService.findAll();
        model.addAttribute("reservations", reservations);

        return "reservation-list";
    }

    @GetMapping("/create")
    public String showAddForm(Model model, @RequestParam(name = "id") int id) {
        List<Employee> employees = employeeService.findAll();
        List<Workspace> workspaces = workspaceService.findWorkspacesByOfficeId(id);
        ReservationForm reservationForm = new ReservationForm();
        reservationForm.setOfficeId(id);
        model.addAttribute("workspaces", workspaces);
        model.addAttribute("employees", employees);
        model.addAttribute("reservationForm", reservationForm);

        return "reservation-form";
    }

    @PostMapping("/save")
    public String saveReservation(@Valid @ModelAttribute ReservationForm reservationForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            int officeId = reservationForm.getOfficeId();
            return "redirect:create?id=" + officeId + "&Error";
        }
        reservationService.createReservationFromForm(reservationForm);
        return "redirect:list";
    }

    @GetMapping("/delete")
    public String deleteReservation(@RequestParam("id") int id) {
        reservationService.deleteById(id);
        return "redirect:list";
    }
}
