package com.bartoszthielmann.employeemanager.service;

import com.bartoszthielmann.employeemanager.dao.user.UserDao;
import com.bartoszthielmann.employeemanager.dao.reservation.ReservationDao;
import com.bartoszthielmann.employeemanager.dao.workspace.WorkspaceDao;
import com.bartoszthielmann.employeemanager.dto.ReservationFormDto;
import com.bartoszthielmann.employeemanager.entity.Reservation;
import com.bartoszthielmann.employeemanager.dto.ReservationDto;
import com.bartoszthielmann.employeemanager.exception.WorkspaceNotAvailableException;
import com.bartoszthielmann.employeemanager.mapper.ReservationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private ReservationDao reservationDao;
    private UserDao userDao;
    private WorkspaceDao workspaceDao;
    private ReservationMapper reservationMapper;

    public ReservationService(ReservationDao reservationDao, UserDao userDao, WorkspaceDao workspaceDao,
                              ReservationMapper reservationMapper) {
        this.reservationDao = reservationDao;
        this.userDao = userDao;
        this.workspaceDao = workspaceDao;
        this.reservationMapper = reservationMapper;
    }

    public List<ReservationDto> findAll() {
        List<Reservation> reservations = reservationDao.findAll();
        List<ReservationDto> reservationDtoList = new ArrayList<>();
        reservations.forEach(reservation ->
                reservationDtoList.add(reservationMapper.reservationToReservationDto(reservation)));

        return reservationDtoList;
    }

    public List<ReservationDto> findWorkspaceReservationsBetweenDates(Integer workspaceId, Date start, Date end) {
        List<Reservation> reservationsList = reservationDao
                .findWorkspaceReservationsBetweenDates(workspaceId, start, end);
        List <ReservationDto> reservationDtoList = new ArrayList<>();
        reservationsList.forEach(reservation ->
                reservationDtoList.add(reservationMapper.reservationToReservationDto(reservation)));

        return reservationDtoList;
    }

    @Transactional
    public Reservation save(ReservationDto reservationDto) {
        Date start = reservationDto.getStart();
        Date end = reservationDto.getEnd();
        Integer workspaceId = reservationDto.getWorkspace().getId();
        List<Reservation> existingReservations = reservationDao
                .findWorkspaceReservationsBetweenDates(workspaceId, start, end);
        if (!existingReservations.isEmpty()) {
            throw new WorkspaceNotAvailableException();
        }
        Reservation reservation = reservationMapper.reservationDtoToReservation(reservationDto);

        return reservationDao.save(reservation);
    }

    @Transactional
    public Reservation save(ReservationFormDto reservationFormDto) {
        Date start = reservationFormDto.getStart();
        Date end = reservationFormDto.getEnd();
        Integer workspaceId = reservationFormDto.getWorkspaceId();
        List<Reservation> existingReservations = reservationDao
                .findWorkspaceReservationsBetweenDates(workspaceId, start, end);
        if (!existingReservations.isEmpty()) {
            throw new WorkspaceNotAvailableException();
        }
        Reservation reservation = new Reservation();
        reservation.setUser(userDao.findById(reservationFormDto.getUserId()));
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
