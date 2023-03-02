package ru.kata.spring.boot_security.demo.service;

//import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;

//@AllArgsConstructor
//@NoArgsConstructor
@Service
public class LoadUserService {
    private final UserService  userService;

    public LoadUserService(UserService userService) {
        this.userService = userService;
    }
    public User loadUserSecurely(String username) {
        return (User) userService.loadUserByUsername(username);
    }
}
