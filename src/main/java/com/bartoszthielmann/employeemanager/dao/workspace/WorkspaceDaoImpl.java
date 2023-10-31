package com.bartoszthielmann.employeemanager.dao.workspace;

import com.bartoszthielmann.employeemanager.entity.Workspace;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WorkspaceDaoImpl implements WorkspaceDao {

    private EntityManager entityManager;

    public WorkspaceDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Workspace> findAll() {
        TypedQuery<Workspace> query = entityManager.createQuery("FROM Workspace", Workspace.class);
        return query.getResultList();
    }

    @Override
    public Workspace findById(int id) {
        return entityManager.find(Workspace.class, id);
    }

    @Override
    public void deleteById(int id) {
        Workspace workspace = entityManager.find(Workspace.class, id);
        if (workspace != null) {
            entityManager.remove(workspace);
        }
    }

    @Override
    public void save(Workspace workspace) {
        entityManager.merge(workspace);
    }

    @Override
    public List<Workspace> findWorkspacesByOfficeId(int id) {
        TypedQuery<Workspace> query = entityManager.createQuery(
                "FROM Workspace WHERE office.id = :id", Workspace.class);
        query.setParameter("id", id);

        return query.getResultList();
    }
}
