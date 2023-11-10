package com.bartoszthielmann.employeemanager.service;

import com.bartoszthielmann.employeemanager.dao.user.UserDao;
import com.bartoszthielmann.employeemanager.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements FieldValueExists {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
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
    public void save(User user) {
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
