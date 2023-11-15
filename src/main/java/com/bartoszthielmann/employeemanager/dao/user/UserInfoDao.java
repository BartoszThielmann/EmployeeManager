package com.bartoszthielmann.employeemanager.dao.user;


import com.bartoszthielmann.employeemanager.entity.UserInfo;

public interface UserInfoDao {

    public UserInfo findByUserId(int id);

    public boolean exists(String fieldName, String value, Integer ignoredId);
}
