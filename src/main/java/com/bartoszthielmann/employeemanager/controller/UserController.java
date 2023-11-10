package com.bartoszthielmann.employeemanager.controller;

import com.bartoszthielmann.employeemanager.entity.User;
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

//    @GetMapping("/delete")
//    public String deleteEmployee(@RequestParam("id") int id) {
//        employeeService.deleteById(id);
//
//        return "redirect:list";
//    }
//
//    @GetMapping("/create")
//    public String showAddForm(Model model) {
//        model.addAttribute("employee", new Employee());
//
//        return "employee-form";
//    }
//
//    @GetMapping("/update")
//    public String showUpdateForm(@RequestParam("id") int id, Model model) {
//        Employee employee = employeeService.findById(id);
//        model.addAttribute("employee", employee);
//
//        return "employee-form";
//    }
//
//    @PostMapping("/save")
//    public String saveEmployee(@Valid @ModelAttribute Employee employee, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "employee-form";
//        }
//        employeeService.save(employee);
//        return "redirect:list";
//    }
}
