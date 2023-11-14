package com.bartoszthielmann.employeemanager.dao.user;

import com.bartoszthielmann.employeemanager.entity.UserInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserInfoDaoImpl implements UserInfoDao {

    private EntityManager entityManager;

    public UserInfoDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
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
