package com.bartoszthielmann.employeemanager.mapper;

import com.bartoszthielmann.employeemanager.dto.UserDto;
import com.bartoszthielmann.employeemanager.entity.User;
import org.mapstruct.Mapper;

@Mapper(uses = RoleMapper.class)
public interface UserMapper {

    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);
}
