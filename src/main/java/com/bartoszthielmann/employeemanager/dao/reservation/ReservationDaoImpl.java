package com.bartoszthielmann.employeemanager.dao.reservation;

import com.bartoszthielmann.employeemanager.entity.Reservation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.sql.Date;
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

    public List<Reservation> findWorkspaceReservationsBetweenDates(Integer workspaceId, Date start, Date end) {
        TypedQuery<Reservation> query = entityManager.createQuery(
                "SELECT r " +
                        "FROM Reservation r JOIN r.workspace w " +
                        "WHERE (w.id = :workspaceId) " +
                        "AND ((r.start BETWEEN :start AND :end) " +
                        "OR (r.end BETWEEN :start AND :end) " +
                        "OR (r.start < :start AND r.end > :end))", Reservation.class);
        query.setParameter("workspaceId", workspaceId);
        query.setParameter("start", start);
        query.setParameter("end", end);
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
