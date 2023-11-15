package com.bartoszthielmann.employeemanager.service;


import com.bartoszthielmann.employeemanager.dao.user.UserInfoDao;
import com.bartoszthielmann.employeemanager.entity.UserInfo;
import org.springframework.stereotype.Service;


@Service
public class UserInfoService implements FieldValueExists {

    private UserInfoDao userInfoDao;

    public UserInfoService(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    public UserInfo findByUserId(int id) {
        return userInfoDao.findByUserId(id);
    }

    @Override
    public boolean fieldValueExists(String fieldName, Object value, Object ignoredId) {
        return userInfoDao.exists(fieldName, (String) value, (Integer) ignoredId);
    }
}
