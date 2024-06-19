package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    List<Role> getAllRoles();

    void addRole(Role role);

    Role getRoleById(Long id);

    Set<Role> findAllRoleId(List<Long> id);

}
