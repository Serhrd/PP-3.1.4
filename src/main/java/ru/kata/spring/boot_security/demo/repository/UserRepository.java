package ru.kata.spring.boot_security.demo.repository;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();

    User getById(Long id);

    void save(User user);

    void updateUser(User user);

    void deleteById(Long id);

    User findByUsername(String username);

}
