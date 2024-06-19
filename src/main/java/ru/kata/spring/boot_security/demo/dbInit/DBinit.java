package ru.kata.spring.boot_security.demo.dbInit;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


@Component
public class DBinit {

    UserService us;
    RoleService rs;
    BCryptPasswordEncoder pe;

    @Autowired
    public DBinit(UserService userService, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.us = userService;
        this.rs = roleService;
        this.pe = bCryptPasswordEncoder;
    }
    @Transactional
    @PostConstruct
    public void run() {

        rs.addRole(new Role("ROLE_ADMIN"));
        rs.addRole(new Role("ROLE_USER"));

        Set<Role> adminRole = new HashSet<>();
        Set<Role> userRole = new HashSet<>();
        Set<Role> aU = new HashSet<>();
        adminRole.add(rs.getRoleById(1L));
        userRole.add(rs.getRoleById(2L));
        aU.add(rs.getRoleById(1L));
        aU.add(rs.getRoleById(2L));

        us.updateUser(new User("admin", "ad", "admin", pe.encode("admin"), adminRole));
        us.updateUser(new User("user", "us", "user", pe.encode("user"), userRole));
        us.updateUser(new User("adminUser", "au", "au", pe.encode("au"), aU));
    }
}
