package com.bartoszthielmann.employeemanager.service;

import com.bartoszthielmann.employeemanager.dao.employee.EmployeeDao;
import com.bartoszthielmann.employeemanager.dao.reservation.ReservationDao;
import com.bartoszthielmann.employeemanager.dao.workspace.WorkspaceDao;
import com.bartoszthielmann.employeemanager.entity.Reservation;
import com.bartoszthielmann.employeemanager.entity.ReservationForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationService {

    private ReservationDao reservationDao;
    private EmployeeDao employeeDao;
    private WorkspaceDao workspaceDao;

    public ReservationService(ReservationDao reservationDao, EmployeeDao employeeDao, WorkspaceDao workspaceDao) {
        this.reservationDao = reservationDao;
        this.employeeDao = employeeDao;
        this.workspaceDao = workspaceDao;
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    @Transactional
    public void save(Reservation reservation) {
        reservationDao.save(reservation);
    }

    @Transactional
    public void createReservationFromForm(ReservationForm reservationForm) {
        Reservation reservation = new Reservation();
        reservation.setEmployee(employeeDao.findById(reservationForm.getEmployeeId()));
        reservation.setWorkspace(workspaceDao.findById(reservationForm.getWorkspaceId()));
        reservation.setStart(reservationForm.getStart());
        reservation.setEnd(reservationForm.getEnd());
        reservationDao.save(reservation);
    }
}
