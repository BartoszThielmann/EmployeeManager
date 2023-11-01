package com.bartoszthielmann.employeemanager.controller;

import com.bartoszthielmann.employeemanager.entity.employee.Employee;
import com.bartoszthielmann.employeemanager.entity.Reservation;
import com.bartoszthielmann.employeemanager.entity.ReservationForm;
import com.bartoszthielmann.employeemanager.entity.Workspace;
import com.bartoszthielmann.employeemanager.service.EmployeeService;
import com.bartoszthielmann.employeemanager.service.ReservationService;
import com.bartoszthielmann.employeemanager.service.WorkspaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        Reservation reservation = new Reservation();
        model.addAttribute("workspaces", workspaces);
        model.addAttribute("employees", employees);
        model.addAttribute("reservationForm", new ReservationForm());

        return "reservation-form";
    }

    @PostMapping("/save")
    public String saveReservation(@ModelAttribute ReservationForm reservationForm) {
        reservationService.createReservationFromForm(reservationForm);
        return "redirect:list";
    }
}
