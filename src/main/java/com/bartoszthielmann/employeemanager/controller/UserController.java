package com.bartoszthielmann.employeemanager.controller;

import com.bartoszthielmann.employeemanager.entity.User;
import com.bartoszthielmann.employeemanager.entity.UserDto;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String showUsers(Model model) {
        List<User> users = userService.findAll();
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
        model.addAttribute("user", new UserDto());
        return "userForm";
    }

//    @GetMapping("/update")
//    public String showUpdateForm(@RequestParam("id") int id, Model model) {
//        User user = userService.findById(id);
//        model.addAttribute("user", user);
//
//        return "userForm";
//    }

    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute UserDto user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "userForm";
        }
        userService.save(user);
        return "redirect:list";
    }
}
