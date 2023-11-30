package com.bartoszthielmann.employeemanager.service;

import com.bartoszthielmann.employeemanager.dao.user.UserDao;
import com.bartoszthielmann.employeemanager.dao.reservation.ReservationDao;
import com.bartoszthielmann.employeemanager.dao.workspace.WorkspaceDao;
import com.bartoszthielmann.employeemanager.entity.Reservation;
import com.bartoszthielmann.employeemanager.entity.ReservationDto;
import com.bartoszthielmann.employeemanager.entity.Workspace;
import com.bartoszthielmann.employeemanager.exception.WorkspaceNotAvailableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private ReservationDao reservationDao;
    private UserDao userDao;
    private WorkspaceDao workspaceDao;

    public ReservationService(ReservationDao reservationDao, UserDao userDao, WorkspaceDao workspaceDao) {
        this.reservationDao = reservationDao;
        this.userDao = userDao;
        this.workspaceDao = workspaceDao;
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
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
