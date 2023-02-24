package ru.kata.spring.boot_security.demo.service;



//import com.hkl.pp_3_1_2_crud_boot.model.User;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    public void addUser(User user);
    public User getUser(User user);
    public User getUser(Long id);
    //public void deleteUser(long id);
    public void deleteUser(User user);
    public void updateUser(User user);
    public List<?> getAllUsers();
    public List<?> getAllRoles();
}
