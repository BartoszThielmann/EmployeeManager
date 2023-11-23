package com.bartoszthielmann.employeemanager.controller;

import com.bartoszthielmann.employeemanager.entity.Reservation;
import com.bartoszthielmann.employeemanager.entity.ReservationDto;
import com.bartoszthielmann.employeemanager.entity.User;
import com.bartoszthielmann.employeemanager.entity.Workspace;
import com.bartoszthielmann.employeemanager.exception.WorkspaceNotAvailableException;
import com.bartoszthielmann.employeemanager.security.CustomUserDetails;
import com.bartoszthielmann.employeemanager.service.UserService;
import com.bartoszthielmann.employeemanager.service.ReservationService;
import com.bartoszthielmann.employeemanager.service.WorkspaceService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
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
    private UserService userService;

    public ReservationController(ReservationService reservationService, WorkspaceService workspaceService,
                                 UserService userService) {
        this.reservationService = reservationService;
        this.workspaceService = workspaceService;
        this.userService = userService;
    }

    @GetMapping("/list")
    public String showReservations(Model model) {
        List<Reservation> reservations = reservationService.findAll();
        model.addAttribute("reservations", reservations);

        return "reservation-list";
    }

    @GetMapping("/create")
    public String showAddForm(Model model, @RequestParam(name = "id") int id) {
        List<User> users = userService.findAll();
        List<Workspace> workspaces = workspaceService.findWorkspacesByOfficeId(id);
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setOfficeId(id);
        model.addAttribute("workspaces", workspaces);
        model.addAttribute("reservationDto", reservationDto);

        return "reservation-form";
    }

    @PostMapping("/save")
    public String saveReservation(@Valid @ModelAttribute ReservationDto reservationDto, BindingResult bindingResult,
                                  Authentication authentication) {
        if (bindingResult.hasErrors()) {
            int officeId = reservationDto.getOfficeId();
            return "redirect:create?id=" + officeId + "&Error";
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        reservationDto.setUserId(userDetails.getId());
        Reservation reservation;
        try {
            reservation = reservationService.createReservationFromDto(reservationDto);
        } catch (WorkspaceNotAvailableException e) {
            int officeId = reservationDto.getOfficeId();
            return "redirect:create?id=" + officeId + "&NotAvailable";
        }
        reservationService.save(reservation);

        return "redirect:list";
    }

    @GetMapping("/delete")
    public String deleteReservation(@RequestParam("id") int id) {
        reservationService.deleteById(id);
        return "redirect:list";
    }
}
