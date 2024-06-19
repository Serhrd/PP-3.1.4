package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService us;

    @Autowired
    public UserController(UserService userServices) {
        this.us = userServices;
    }
    @GetMapping
    public String getAllUsers(Principal principal, Model model ) {
        model.addAttribute("user", us.getUserByUsername(principal.getName()));
        return "/user";
    }

    @GetMapping("/form")
    public String getUser(Principal principal, Model model ) {
        model.addAttribute("user", us.getUserByUsername(principal.getName()));
        return "/user_form";
    }
}
