package com.bartoszthielmann.employeemanager.dao.user;

import com.bartoszthielmann.employeemanager.entity.Role;
import com.bartoszthielmann.employeemanager.entity.User;

import java.util.List;

public interface UserDao {


    public List<User> findAll();

    public User findById(int id);

    public User findByUsername(String username);

    public List<String> findUsernamesByPrefix(String prefix);

    public List<Role> findAllRoles();

    public List<Role> findRolesByIds(List<Integer> idList);

    public void deleteById(int id);

    public User save(User user);

    public boolean exists(String fieldName, String value, Integer ignoredId);
}
