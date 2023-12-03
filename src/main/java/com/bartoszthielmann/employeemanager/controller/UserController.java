package com.bartoszthielmann.employeemanager.controller;

import com.bartoszthielmann.employeemanager.dto.RoleDto;
import com.bartoszthielmann.employeemanager.dto.UserInfoDto;
import com.bartoszthielmann.employeemanager.dto.UserFormDto;
import com.bartoszthielmann.employeemanager.service.UserInfoService;
import com.bartoszthielmann.employeemanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private UserInfoService userInfoService;

    public UserController(UserService userService, UserInfoService userInfoService) {
        this.userService = userService;
        this.userInfoService = userInfoService;
    }

    @GetMapping("/list")
    public String showUsers(Model model) {
        List<UserInfoDto> users = userInfoService.findAll();
        model.addAttribute("users", users);

        return "listUsers";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") int id) {
        userService.deleteById(id);

        return "redirect:list";
    }

    @GetMapping("/create")
    public String showAddForm(Model model) {
        List<RoleDto> roles = userService.findAllRoles();
        model.addAttribute("user", new UserFormDto());
        model.addAttribute("roles", roles);
        return "userForm";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("id") int id, Model model) {
        UserFormDto userFormDto = userService.createUserFormDto(id);
        List<RoleDto> roles = userService.findAllRoles();
        model.addAttribute("user", userFormDto);
        model.addAttribute("roles", roles);
        return "userForm";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("user") @Valid UserFormDto userFormDto, BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            List<RoleDto> roles = userService.findAllRoles();
            model.addAttribute("roles", roles);
            return "userForm";
        }
        userService.save(userFormDto);
        return "redirect:list";
    }
}
