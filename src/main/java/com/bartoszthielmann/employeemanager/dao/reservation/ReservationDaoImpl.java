package com.bartoszthielmann.employeemanager.dao.reservation;

import com.bartoszthielmann.employeemanager.entity.Reservation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationDaoImpl implements ReservationDao {

    private EntityManager entityManager;

    public ReservationDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Reservation> findAll() {
        TypedQuery<Reservation> query = entityManager.createQuery("FROM Reservation", Reservation.class);
        return query.getResultList();
    }

    @Override
    public Reservation findById(int id) {
        return entityManager.find(Reservation.class, id);
    }

    @Override
    public void deleteById(int id) {
        Reservation reservation = entityManager.find(Reservation.class, id);
        if (reservation != null) {
            entityManager.remove(reservation);
        }
    }

    @Override
    public void save(Reservation reservation) {
        entityManager.merge(reservation);
    }
}
