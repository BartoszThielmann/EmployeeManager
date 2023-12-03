package com.bartoszthielmann.employeemanager.service;

import com.bartoszthielmann.employeemanager.dao.user.UserDao;
import com.bartoszthielmann.employeemanager.dao.reservation.ReservationDao;
import com.bartoszthielmann.employeemanager.dao.workspace.WorkspaceDao;
import com.bartoszthielmann.employeemanager.entity.Reservation;
import com.bartoszthielmann.employeemanager.entity.ReservationDto;
import com.bartoszthielmann.employeemanager.entity.Workspace;
import com.bartoszthielmann.employeemanager.exception.WorkspaceNotAvailableException;
import com.bartoszthielmann.employeemanager.mapper.ReservationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private ReservationDao reservationDao;
    private UserDao userDao;
    private WorkspaceDao workspaceDao;
    private ReservationMapper reservationMapper;

    public ReservationService(ReservationDao reservationDao, UserDao userDao, WorkspaceDao workspaceDao, ReservationMapper reservationMapper) {
        this.reservationDao = reservationDao;
        this.userDao = userDao;
        this.workspaceDao = workspaceDao;
        this.reservationMapper = reservationMapper;
    }

    public List<com.bartoszthielmann.employeemanager.dto.ReservationDto> findAll() {
        List<Reservation> reservations = reservationDao.findAll();
        List<com.bartoszthielmann.employeemanager.dto.ReservationDto> reservationDtoList = new ArrayList<>();
        reservations.forEach(reservation -> reservationDtoList.add(reservationMapper.reservationToReservationDto(reservation)));

        return reservationDtoList;
    }

    public List<ReservationDto> findWorkspaceReservationsBetweenDates(Integer workspaceId, Date start, Date end) {
        List<Reservation> reservationsList = reservationDao.findWorkspaceReservationsBetweenDates(workspaceId, start, end);
        // Map Reservation to ReservationDto
        List <ReservationDto> reservationDtoList = reservationsList.stream()
                .map(reservation -> {
                    ReservationDto reservationDto = new ReservationDto();
                    reservationDto.setStart(reservation.getStart());
                    reservationDto.setEnd(reservation.getEnd());
                    reservationDto.setUserId(reservation.getUser().getId());
                    reservationDto.setWorkspaceId(reservation.getWorkspace().getId());
                    return reservationDto;
                }).collect(Collectors.toList());

        return reservationDtoList;
    }

    @Transactional
    public Reservation save(Reservation reservation) {
        return reservationDao.save(reservation);
    }

    @Transactional
    public Reservation createReservationFromDto(ReservationDto reservationDto) {
        Date start = reservationDto.getStart();
        Date end = reservationDto.getEnd();
        Integer workspaceId = reservationDto.getWorkspaceId();
        List<Reservation> existingReservations = reservationDao
                .findWorkspaceReservationsBetweenDates(workspaceId, start, end);
        if (!existingReservations.isEmpty()) {
            throw new WorkspaceNotAvailableException();
        }
        Reservation reservation = new Reservation();
        reservation.setUser(userDao.findById(reservationDto.getUserId()));
        reservation.setWorkspace(workspaceDao.findById(workspaceId));
        reservation.setStart(start);
        reservation.setEnd(end);
        return reservation;
    }

    @Transactional
    public void deleteById(int id) {
        reservationDao.deleteById(id);
    }
}
