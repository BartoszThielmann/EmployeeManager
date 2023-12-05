package com.bartoszthielmann.employeemanager.dao.office;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import com.bartoszthielmann.employeemanager.entity.Office;

import java.util.List;

@Repository
public class OfficeDaoImpl implements OfficeDao {

    private EntityManager entityManager;

    public OfficeDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Office> findAll() {
        TypedQuery<Office> query = entityManager.createQuery("FROM Office", Office.class);
        return query.getResultList();
    }

    @Override
    public Office findById(int id) {
        return entityManager.find(Office.class, id);
    }

    public Office findByWorkspaceId(int id) {
        TypedQuery<Office> query = entityManager.createQuery(
                "SELECT o FROM Office o JOIN o.workspaces w WHERE w.id = :workspaceId", Office.class);
        query.setParameter("workspaceId", id);

        return query.getSingleResult();
    }

    @Override
    public void deleteById(int id) {
        Office office = entityManager.find(Office.class, id);
        if (office != null) {
            entityManager.remove(office);
        }
    }

    @Override
    public void save(Office office) {
        entityManager.merge(office);
    }

}
