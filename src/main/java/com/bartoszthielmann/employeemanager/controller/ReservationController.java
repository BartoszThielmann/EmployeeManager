package com.bartoszthielmann.employeemanager.controller;

import com.bartoszthielmann.employeemanager.dto.OfficeDto;
import com.bartoszthielmann.employeemanager.dto.ReservationFormDto;
import com.bartoszthielmann.employeemanager.dto.WorkspaceDto;
import com.bartoszthielmann.employeemanager.dto.ReservationDto;
import com.bartoszthielmann.employeemanager.exception.WorkspaceNotAvailableException;
import com.bartoszthielmann.employeemanager.security.CustomUserDetails;
import com.bartoszthielmann.employeemanager.service.OfficeService;
import com.bartoszthielmann.employeemanager.service.UserService;
import com.bartoszthielmann.employeemanager.service.ReservationService;
import com.bartoszthielmann.employeemanager.service.WorkspaceService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private ReservationService reservationService;
    private WorkspaceService workspaceService;
    private UserService userService;
    private OfficeService officeService;

    public ReservationController(ReservationService reservationService, WorkspaceService workspaceService,
                                 UserService userService, OfficeService officeService) {
        this.reservationService = reservationService;
        this.workspaceService = workspaceService;
        this.userService = userService;
        this.officeService = officeService;
    }

    @GetMapping("/list")
    public String showReservations(Model model) {
        List<ReservationDto> reservations = reservationService.findAll();
        Map<ReservationDto, OfficeDto> reservationOfficeMap = new HashMap<>();
        // OfficeDto is retrieved from database for every Reservation
        // This is probably not the most efficient way to do this
        reservations.forEach(reservation -> {
            int workspaceId = reservation.getWorkspace().getId();
            OfficeDto office = officeService.findOfficeByWorkspaceId(workspaceId);
            reservationOfficeMap.put(reservation, office);
        });
        model.addAttribute("reservations", reservationOfficeMap);

        return "reservation-list";
    }

    @GetMapping("/create")
    public String showAddForm(Model model, @RequestParam(name = "id") int id,
                              @RequestParam(name="workspaceId", required = false) Integer workspaceId,
                              @RequestParam(name="startDate", required = false) Date startDate,
                              @RequestParam(name="endDate", required = false) Date endDate) {
        List<WorkspaceDto> workspaces = workspaceService.findWorkspacesByOfficeId(id);
        ReservationFormDto reservationFormDto = new ReservationFormDto();
        reservationFormDto.setOfficeId(id);
        model.addAttribute("workspaces", workspaces);
        model.addAttribute("reservationDto", reservationFormDto);

        if (workspaceId != null && startDate != null && endDate != null) {
            List<ReservationDto> conflictingReservations = reservationService
                    .findWorkspaceReservationsBetweenDates(workspaceId, startDate, endDate);
            model.addAttribute("conflictingReservations", conflictingReservations);

            List<WorkspaceDto> freeWorkspaces = workspaceService
                    .findAvailableWorkspacesInOfficeBetweenDates(id, startDate, endDate);
            model.addAttribute("freeWorkspaces", freeWorkspaces);

            WorkspaceDto workspaceDto = workspaceService.findById(workspaceId);
            model.addAttribute("selectedWorkspace", workspaceDto);
            model.addAttribute("selectedStart", startDate);
            model.addAttribute("selectedEnd", endDate);
        }

        return "reservation-form";
    }

    @PostMapping("/save")
    public String saveReservation(@Valid @ModelAttribute ReservationFormDto reservationFormDto,
                                  BindingResult bindingResult, Authentication authentication) {
        int officeId = reservationFormDto.getOfficeId();
        if (bindingResult.hasErrors()) {
            return "redirect:create?id=" + officeId + "&Error";
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Integer userId = userDetails.getId();
        reservationFormDto.setUserId(userId);
        try {
            reservationService.save(reservationFormDto);
        } catch (WorkspaceNotAvailableException e) {
            return "redirect:create?id=" + officeId + "&NotAvailable"
                    + "&workspaceId=" + reservationFormDto.getWorkspaceId()
                    + "&startDate=" + reservationFormDto.getStart()
                    + "&endDate=" + reservationFormDto.getEnd();
        }

        return "redirect:list";
    }

    @GetMapping("/delete")
    public String deleteReservation(@RequestParam("id") int id) {
        reservationService.deleteById(id);
        return "redirect:list";
    }
}
