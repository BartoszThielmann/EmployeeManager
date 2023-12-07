package com.bartoszthielmann.employeemanager.service;

import com.bartoszthielmann.employeemanager.dao.user.UserDao;
import com.bartoszthielmann.employeemanager.dao.user.UserInfoDao;
import com.bartoszthielmann.employeemanager.dto.RoleDto;
import com.bartoszthielmann.employeemanager.dto.UserDto;
import com.bartoszthielmann.employeemanager.entity.Role;
import com.bartoszthielmann.employeemanager.entity.User;
import com.bartoszthielmann.employeemanager.dto.UserFormDto;
import com.bartoszthielmann.employeemanager.entity.UserInfo;
import com.bartoszthielmann.employeemanager.mapper.RoleMapper;
import com.bartoszthielmann.employeemanager.mapper.UserMapper;
import jakarta.validation.ConstraintViolation;
import org.hibernate.exception.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.lang.reflect.Array;

@Service
public class UserService implements FieldValueExists {

    private UserDao userDao;
    private UserInfoDao userInfoDao;
    private PasswordEncoder passwordEncoder;
    private Validator validator;
    private RoleMapper roleMapper;
    private UserMapper userMapper;

    public UserService(UserDao userDao, UserInfoDao userInfoDao, PasswordEncoder passwordEncoder,
                       Validator validator, RoleMapper roleMapper, UserMapper userMapper) {
        this.userDao = userDao;
        this.userInfoDao = userInfoDao;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
        this.roleMapper = roleMapper;
        this.userMapper = userMapper;
    }

    public List<UserDto> findAll() {
        List<User> users = userDao.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        users.forEach(user -> userDtoList.add(userMapper.userToUserDto(user)));
        return userDtoList;
    }

    public UserDto findById(int id) {
        User user = userDao.findById(id);
        return userMapper.userToUserDto(user);
    }

    public List<RoleDto> findAllRoles() {
        List<Role> roles = userDao.findAllRoles();
        List<RoleDto> roleDtoList = new ArrayList<>();
        roles.forEach(role -> roleDtoList.add(roleMapper.roleToRoleDto(role)));
        return roleDtoList;
    }

    public List<RoleDto> findRolesByIds(List<Integer> idList) {
        List<Role> roles = userDao.findRolesByIds(idList);
        List<RoleDto> roleDtoList = new ArrayList<>();
        roles.forEach(role -> roleDtoList.add(roleMapper.roleToRoleDto(role)));
        return roleDtoList;
    }

    public UserFormDto createUserFormDto(Integer userId) {
        User user = userDao.findById(userId);
        UserInfo userInfo = user.getUserInfo();
        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setId(user.getId());
        userFormDto.setFirstName(userInfo.getFirstName());
        userFormDto.setLastName(userInfo.getLastName());
        List<String> rolesList = new ArrayList<>();
        for (Role role : user.getRoles()) {
            rolesList.add(String.valueOf(role.getId()));
        }
        userFormDto.setRoles(rolesList);
        return userFormDto;
    }

    @Transactional
    public void deleteById(int id) {
        userDao.deleteById(id);
    }

    @Transactional
    public User save(UserFormDto userFormDto) {
        User user = new User();
        String firstName = userFormDto.getFirstName();
        String lastName = userFormDto.getLastName();
        String username = generateStandardUsername(userFormDto);

        user.setId(userFormDto.getId());
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(userFormDto.getPassword()));
        user.setEnabled(true);

        List<String> roles = userFormDto.getRoles();
        if (roles != null) {
            List<Integer> rolesIds = new ArrayList<>();
            for (String str : roles) {
                try {
                    rolesIds.add(Integer.parseInt(str));
                } catch (NumberFormatException e) {
                    // No need to do anything for now
                }
            }
            if (!rolesIds.isEmpty()) {
                Set<Role> rolesSet = new HashSet<Role>(userDao.findRolesByIds(rolesIds));
                user.setRoles(rolesSet);
            }
        }

        UserInfo userInfo;
        if (userFormDto.getId() != 0) {
            userInfo = userInfoDao.findByUserId(userFormDto.getId());
        } else {
            userInfo = new UserInfo();
        }

        userInfo.setFirstName(firstName);
        userInfo.setLastName(lastName);
        userInfo.setEmail(username + "@bth.com");
        userInfo.setUser(user);

        Set<ConstraintViolation<User>> userViolations = validator.validate(user);
        if (!userViolations.isEmpty()) {
            ConstraintViolation<User> userViolation =
                    (ConstraintViolation<User>) Array.get(userViolations.toArray(), 0);
            throw new ConstraintViolationException("Violated constraints of User",
                    new SQLException(userViolation.getMessage()), "UserConstraint");
        }
        Set<ConstraintViolation<UserInfo>> userInfoViolations = validator.validate(userInfo);
        if (!userInfoViolations.isEmpty()) {
            ConstraintViolation<UserInfo> userInfoViolation =
                    (ConstraintViolation<UserInfo>) Array.get(userInfoViolations.toArray(), 0);
            throw new ConstraintViolationException("Violated constraints of UserInfo",
                    new SQLException(userInfoViolation.getMessage()), "UserInfoConstraint");
        }
        return userDao.save(user);
    }

    @Override
    public boolean fieldValueExists(String fieldName, Object value, Object ignoredId) {
        if (value == null) {
            return false;
        }
        return userDao.exists(fieldName, (String) value, (Integer) ignoredId);
    }

    private String generateStandardUsername(UserFormDto userFormDto) {
        String baseUsername = (userFormDto.getFirstName().charAt(0) + userFormDto.getLastName()).toLowerCase();
        List<String> emails = userDao.findUsernamesByPrefixAndIgnoreUserId(baseUsername, userFormDto.getId());
        // Filter for usernames with standard username pattern
        final Pattern p1 = Pattern.compile(baseUsername + "\\d*$");
        List<String> standardUsernames = emails.stream()
                .filter(s -> p1.matcher(s).matches()).collect(Collectors.toList());
        if (standardUsernames.isEmpty()) {
            // baseUsername doesn't already exist in the database
            return baseUsername;
        } else {
            List<Integer> numbers = new ArrayList<>();
            Pattern p2 = Pattern.compile("(?<username>" + baseUsername + ")" + "(?<number>\\d+)$");
            for (String usernameItem : standardUsernames) {
                Matcher m = p2.matcher(usernameItem);
                if (m.matches()) {
                    numbers.add(Integer.parseInt(m.group("number")));
                }
            }
            if (numbers.isEmpty()) {
                // baseUsername without number already exists in database, so add number
                return baseUsername + "2";
            } else {
                // baseUsername with number already exists in database, so increment the highest number
                return baseUsername + String.valueOf(Collections.max(numbers) + 1);
            }
        }
    }
}
