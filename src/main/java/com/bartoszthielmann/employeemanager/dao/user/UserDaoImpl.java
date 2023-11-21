package com.bartoszthielmann.employeemanager.dao.user;

import com.bartoszthielmann.employeemanager.entity.Role;
import com.bartoszthielmann.employeemanager.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private EntityManager entityManager;

    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> query = entityManager.createQuery("FROM User", User.class);
        return query.getResultList();
    }

    @Override
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    public User findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("FROM User WHERE username = :username", User.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    @Override
    public List<String> findUsernamesByPrefix(String prefix) {
        String queryString =
                "SELECT u.username FROM User u WHERE u.username LIKE '" + prefix + "%' ORDER BY u.username";
        TypedQuery<String> query = entityManager.createQuery(queryString, String.class);
        return query.getResultList();
    }

    public List<Role> findAllRoles() {
        TypedQuery<Role> query = entityManager.createQuery("FROM Role", Role.class);
        return query.getResultList();
    }

    public List<Role> findRolesByIds(List<Integer> idList) {
        TypedQuery<Role> query = entityManager.createQuery("FROM Role WHERE id IN :idList", Role.class);
        query.setParameter("idList", idList);
        return query.getResultList();
    }

    @Override
    public void deleteById(int id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public User save(User user) {
        return entityManager.merge(user);
    }

    @Override
    public boolean exists(String fieldName, String value, Integer ignoredId) {
        // Workaround to have named parameter concatenated to a variable
        String queryString = "select u from User u where u." + fieldName + " = :value and id != :ignoredId";
        TypedQuery<User> query = entityManager.createQuery(queryString, User.class);
        query.setParameter("value", value);
        query.setParameter("ignoredId", ignoredId);
        try {
            query.getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }
}
