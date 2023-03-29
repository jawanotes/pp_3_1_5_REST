package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    public void addUser(User user);
    public User getUser(Long id);
    public void deleteUser(User user);
    public void updateUser(User user);
    public List<?> getAllUsers();
}
