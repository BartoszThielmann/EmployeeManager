package com.bartoszthielmann.employeemanager.dao.reservation;

import com.bartoszthielmann.employeemanager.entity.Reservation;

import java.util.List;

public interface ReservationDao {

    public List<Reservation> findAll();

    public Reservation findById(int id);

    public void deleteById(int id);

    public void save(Reservation reservation);
}
