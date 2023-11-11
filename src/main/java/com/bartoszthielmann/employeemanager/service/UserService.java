package com.bartoszthielmann.employeemanager.service;

import com.bartoszthielmann.employeemanager.dao.user.UserDao;
import com.bartoszthielmann.employeemanager.entity.User;
import com.bartoszthielmann.employeemanager.entity.UserDto;
import com.bartoszthielmann.employeemanager.entity.UserInfo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements FieldValueExists {

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User findById(int id) {
        return userDao.findById(id);
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
        String username = (firstName.charAt(0) + lastName).toLowerCase();

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEnabled(true);

        UserInfo userInfo = new UserInfo();
        userInfo.setFirstName(firstName);
        userInfo.setLastName(lastName);
        userInfo.setEmail(username + "@bth.com");
        userInfo.setUser(user);
        user.setUserInfo(userInfo);

        userDao.save(user);
    }

    @Override
    public boolean fieldValueExists(String fieldName, Object value, Object ignoredId) {
        if (value == null) {
            return false;
        }
        return userDao.exists(fieldName, (String) value, (Integer) ignoredId);
    }
}
