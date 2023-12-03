package com.bartoszthielmann.employeemanager.dao.user;


import com.bartoszthielmann.employeemanager.entity.UserInfo;

import java.util.List;

public interface UserInfoDao {

    public List<UserInfo> findAll();

    public UserInfo findByUserId(int id);

    public boolean exists(String fieldName, String value, Integer ignoredId);
}
