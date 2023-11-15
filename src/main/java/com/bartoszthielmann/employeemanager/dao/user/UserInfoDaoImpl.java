package com.bartoszthielmann.employeemanager.dao.user;

import com.bartoszthielmann.employeemanager.entity.UserInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;


@Repository
public class UserInfoDaoImpl implements UserInfoDao {

    private EntityManager entityManager;

    public UserInfoDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public UserInfo findByUserId(int id) {
        TypedQuery<UserInfo> query = entityManager.createQuery(
                "SELECT ui from UserInfo ui JOIN ui.user u WHERE u.id = :id", UserInfo.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public boolean exists(String fieldName, String value, Integer ignoredId) {
        String queryString = "select u from UserInfo u where u." + fieldName + " = :value and id != :ignoredId";
        TypedQuery<UserInfo> query = entityManager.createQuery(queryString, UserInfo.class);
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
