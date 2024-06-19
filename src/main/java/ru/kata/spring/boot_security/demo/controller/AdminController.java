package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RoleService rs;
    private final UserService us;

    @Autowired
    public AdminController(RoleService roleServices, UserService userServices) {
        this.rs = roleServices;
        this.us = userServices;
    }

    @GetMapping(value = "/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", us.getListUsers());
        return "users";
    }

    @GetMapping(value = "")
    public String getAdminPanel(Principal principal, Model model) {
        model.addAttribute("user", us.getUserByUsername(principal.getName()));
        return "users_form";
    }

    @GetMapping(value = "/{id}")
    public String getUserById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", us.getUser(id));
        return "user";
    }

    @GetMapping(value = "/new")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", rs.getAllRoles());
        return "create";
    }

    @PostMapping(value = "/new")
    public String add(@ModelAttribute("user") User user, @RequestParam(name = "ids", required = false) List<Long> ids) {
            Set<Role> role = rs.findAllRoleId(ids);
            user.setRoles(role);
            us.addUser(user);
            return "redirect:/admin";
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        us.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/form/{id}")
    public String deleteUserForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", us.getUser(id));
        model.addAttribute("allRoles", rs.getAllRoles());
        return "delete_form";
    }

    @GetMapping(value = "/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", us.getUser(id));
        model.addAttribute("allRoles", rs.getAllRoles());;
        return "edit";
    }

    @PatchMapping(value = "/save")
    public ResponseEntity<?> update(@ModelAttribute("user") User user,
                                    @RequestParam List<Long> ids) {
        Set<Role> role = rs.findAllRoleId(ids);
        user.setRoles(role);
        us.updateUser(user);
        return ResponseEntity.ok().build();
    }
}
