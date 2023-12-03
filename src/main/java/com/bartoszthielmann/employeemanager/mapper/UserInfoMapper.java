package com.bartoszthielmann.employeemanager.mapper;

import com.bartoszthielmann.employeemanager.dto.UserInfoDto;
import com.bartoszthielmann.employeemanager.entity.UserInfo;
import org.mapstruct.Mapper;

@Mapper(uses = UserMapper.class)
public interface UserInfoMapper {

    UserInfoDto userInfoToUserInfoDto(UserInfo userInfo);
    UserInfo userInfoDtoToUserInfo(UserInfoDto userInfoDto);


}
