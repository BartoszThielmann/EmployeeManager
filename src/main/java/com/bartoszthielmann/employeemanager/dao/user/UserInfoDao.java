package com.bartoszthielmann.employeemanager.dao.user;


public interface UserInfoDao {

    public boolean exists(String fieldName, String value, Integer ignoredId);
}
