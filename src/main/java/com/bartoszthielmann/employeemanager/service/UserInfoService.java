package com.bartoszthielmann.employeemanager.service;


import com.bartoszthielmann.employeemanager.dao.user.UserInfoDao;
import com.bartoszthielmann.employeemanager.dto.UserInfoDto;
import com.bartoszthielmann.employeemanager.entity.UserInfo;
import com.bartoszthielmann.employeemanager.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserInfoService implements FieldValueExists {

    private UserInfoDao userInfoDao;
    private UserInfoMapper userInfoMapper;

    public UserInfoService(UserInfoDao userInfoDao, UserInfoMapper userInfoMapper) {
        this.userInfoDao = userInfoDao;
        this.userInfoMapper = userInfoMapper;
    }

    public List<UserInfoDto> findAll() {
        List<UserInfo> userInfoList = userInfoDao.findAll();
        List<UserInfoDto> userInfoDtoList = new ArrayList<>();
        userInfoList.forEach(userInfo -> userInfoDtoList.add(userInfoMapper.userInfoToUserInfoDto(userInfo)));
        return userInfoDtoList;
    }

    public UserInfo findByUserId(int id) {
        return userInfoDao.findByUserId(id);
    }

    @Override
    public boolean fieldValueExists(String fieldName, Object value, Object ignoredId) {
        return userInfoDao.exists(fieldName, (String) value, (Integer) ignoredId);
    }
}
