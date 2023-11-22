package com.bartoszthielmann.employeemanager.service.user;


import com.bartoszthielmann.employeemanager.dao.user.UserDao;
import com.bartoszthielmann.employeemanager.entity.Role;
import com.bartoszthielmann.employeemanager.entity.User;
import com.bartoszthielmann.employeemanager.entity.UserDto;
import com.bartoszthielmann.employeemanager.entity.UserInfo;
import com.bartoszthielmann.employeemanager.service.UserService;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
// All this looks pretty terrible; probably UserService needs refactoring
    @Mock
    private UserDao userDao;
    @Mock
    private Validator validator;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    private UserDto userDto;
    private String baseUsername;
    private List<Role> roleList;


    @BeforeEach
    public void setup() {
        userDto = new UserDto();
        userDto.setId(0);
        userDto.setFirstName("Joey");
        userDto.setLastName("Salads");
        userDto.setPassword("password123");
        userDto.setRoles(new ArrayList<>(Arrays.asList("1", "2")));

        baseUsername = "jsalads";

        Role role1 = new Role();
        role1.setId(1);
        role1.setName("ROLE_ADMIN");
        Role role2 = new Role();
        role2.setId(2);
        role2.setName("ROLE_USER");
        roleList = new ArrayList<>(Arrays.asList(role1, role2));
    }

    @Test
    public void test_createUserDtoFromUser() {
        // given
        User user = new User();
        user.setId(1);
        user.setUsername("jsalads");
        UserInfo userInfo = new UserInfo();
        userInfo.setFirstName("Joey");
        userInfo.setLastName("Salads");
        user.setUserInfo(userInfo);
        user.setRoles(new HashSet<>(roleList));

        // when
        UserDto userDtoFromUser = userService.createUserDtoFromUser(user);

        // then
        assertThat(userDtoFromUser.getId()).isEqualTo(user.getId());
        assertThat(userDtoFromUser.getFirstName()).isEqualTo(user.getUserInfo().getFirstName());
        assertThat(userDtoFromUser.getLastName()).isEqualTo(user.getUserInfo().getLastName());
        assertThat(userDtoFromUser.getRoles())
                .contains(String.valueOf(roleList.get(0).getId()), String.valueOf(roleList.get(1).getId()));
    }

    @Test
    public void test_save_caseBaseUsername(){
        // given
        given(userDao.findUsernamesByPrefix(baseUsername)).willReturn(Collections.emptyList());
        given(userDao.findRolesByIds(Arrays.asList(1,2))).willReturn(roleList);
        given(passwordEncoder.encode(anyString())).willReturn("password");
        given(validator.validate(any())).willReturn(Collections.emptySet());
        given(userDao.save(any(User.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        User user = userService.save(userDto);

        // then
        assertThat(user.getId()).isEqualTo(userDto.getId());
        assertThat(user.getUserInfo().getFirstName()).isEqualTo(userDto.getFirstName());
        assertThat(user.getUserInfo().getLastName()).isEqualTo(userDto.getLastName());
        assertThat(user.getUserInfo().getEmail()).isEqualTo(baseUsername + "@bth.com");
        assertThat(user.getUsername()).isEqualTo(baseUsername);
        assertThat(user.getPassword()).isEqualTo("password");
    }

    @Test
    public void test_save_caseBaseUsername2(){
        // given
        given(userDao.findUsernamesByPrefix(baseUsername)).willReturn(Arrays.asList("jsalads"));
        given(validator.validate(any())).willReturn(Collections.emptySet());
        given(userDao.save(any(User.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        User user = userService.save(userDto);

        // then
        assertThat(user.getUserInfo().getFirstName()).isEqualTo(userDto.getFirstName());
        assertThat(user.getUserInfo().getLastName()).isEqualTo(userDto.getLastName());
        assertThat(user.getUsername()).isEqualTo(baseUsername + "2");
    }

    @Test
    public void test_save_caseBaseUsernameWithMaxNumberPlusOne() {
        // given
        given(userDao.findUsernamesByPrefix(baseUsername)).willReturn(Arrays.asList("jsalads2"));
        given(validator.validate(any())).willReturn(Collections.emptySet());
        given(userDao.save(any(User.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        User user = userService.save(userDto);

        // then
        assertThat(user.getUserInfo().getFirstName()).isEqualTo(userDto.getFirstName());
        assertThat(user.getUserInfo().getLastName()).isEqualTo(userDto.getLastName());
        assertThat(user.getUsername()).isEqualTo(baseUsername + "3");
    }
}
