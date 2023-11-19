package com.bartoszthielmann.employeemanager.dao.reservation;

import com.bartoszthielmann.employeemanager.entity.Reservation;

import java.sql.Date;
import java.util.List;

public interface ReservationDao {

    public List<Reservation> findAll();

    public Reservation findById(int id);

    public List<Reservation> findWorkspaceReservationsBetweenDates(Integer workspaceId, Date start, Date end);

    public void deleteById(int id);

    public void save(Reservation reservation);
}
