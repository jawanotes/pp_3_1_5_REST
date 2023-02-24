package ru.kata.spring.boot_security.demo.dao;



//import com.hkl.pp_3_1_2_crud_boot.model.User;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@Deprecated
public interface UserDao {
    public void addUser(User user);
    public User getUser(Long id);
    public User getUser(User user);
    //public void deleteUser(long id);
    public void deleteUser(User user);
    public void updateUser(User user);

    public List<?> getAllUsers();
    public User loadUserByUsername(String username);
}
