package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository ur;

    private PasswordEncoder pe;

    @Autowired
    public void setPasswordEncoder(@Lazy PasswordEncoder passwordEncoder) {
        this.pe = passwordEncoder;
    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.ur = userRepository;
    }

    @Override
    public List<User> getListUsers() {
        return ur.findAll();
    }

    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(pe.encode(user.getPassword()));
        ur.save(user);
    }

    @Override
    public User getUser(Long id) {
        return ur.getById(id);
    }
    @Transactional
    @Override
    public void updateUser(User user) {
        ur.updateUser(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return ur.findByUsername(username);
    }
    @Transactional
    @Override
    public void deleteUser(Long id) {
        ur.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return ur.findByUsername(username);
    }
}
