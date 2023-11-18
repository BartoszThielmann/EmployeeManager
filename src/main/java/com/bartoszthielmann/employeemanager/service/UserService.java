package com.bartoszthielmann.employeemanager.service;

import com.bartoszthielmann.employeemanager.dao.user.UserDao;
import com.bartoszthielmann.employeemanager.entity.Role;
import com.bartoszthielmann.employeemanager.entity.User;
import com.bartoszthielmann.employeemanager.entity.UserDto;
import com.bartoszthielmann.employeemanager.entity.UserInfo;
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
    private UserInfoService userInfoService;
    private PasswordEncoder passwordEncoder;
    private Validator validator;

    public UserService(UserDao userDao, UserInfoService userInfoService, PasswordEncoder passwordEncoder,
                       Validator validator) {
        this.userDao = userDao;
        this.userInfoService = userInfoService;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User findById(int id) {
        return userDao.findById(id);
    }

    public List<String> findUsernamesByPrefix(String prefix) {
        return userDao.findUsernamesByPrefix(prefix);
    }

    public List<Role> findAllRoles() {
        return userDao.findAllRoles();
    }

    public List<Role> findRolesByIds(List<Integer> idList) {
        return userDao.findRolesByIds(idList);
    }

    public UserDto createUserDtoFromUser(User user) {
        UserDto userDto = new UserDto();
        UserInfo userInfo = user.getUserInfo();
        userDto.setId(user.getId());
        userDto.setFirstName(userInfo.getFirstName());
        userDto.setLastName(userInfo.getLastName());
        List<String> rolesList = new ArrayList<>();
        for (Role role : user.getRoles()) {
            rolesList.add(String.valueOf(role.getId()));
        }
        userDto.setRoles(rolesList);
        return userDto;
    }

    @Transactional
    public void deleteById(int id) {
        userDao.deleteById(id);
    }

    @Transactional
    public void save(UserDto userDto) {
        User user = new User();
        String firstName = userDto.getFirstName();
        String lastName = userDto.getLastName();
        String username = generateStandardUsername(firstName, lastName);

        user.setId(userDto.getId());
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEnabled(true);

        List<Integer> rolesIds = new ArrayList<>();
        for (String str : userDto.getRoles()) {
            try {
                rolesIds.add(Integer.parseInt(str));
            } catch (NumberFormatException e) {
                // No need to do anything for now
            }
        }
        if (!rolesIds.isEmpty()) {
            Set<Role> roles = new HashSet<Role>(findRolesByIds(rolesIds));
            user.setRoles(roles);
        }

        UserInfo userInfo;
        if (userDto.getId() != 0) {
            userInfo = userInfoService.findByUserId(userDto.getId());
        } else {
            userInfo = new UserInfo();
        }

        userInfo.setFirstName(firstName);
        userInfo.setLastName(lastName);
        userInfo.setEmail(username + "@bth.com");
        user.setUserInfo(userInfo);

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
        userDao.save(user);
    }

    @Override
    public boolean fieldValueExists(String fieldName, Object value, Object ignoredId) {
        if (value == null) {
            return false;
        }
        return userDao.exists(fieldName, (String) value, (Integer) ignoredId);
    }

    private String generateStandardUsername(String firstName, String lastName) {
        String baseUsername = (firstName.charAt(0) + lastName).toLowerCase();
        List<String> emails = findUsernamesByPrefix(baseUsername);
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
