package ru.kata.spring.boot_security.demo.service;

//import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

/**
 * у тебя не должно быть отдельного интерфейса
 * заимплементи стардартный в лоад сервис
 * туда за авторварь дао
 * и вызови метод дао
 */
//@AllArgsConstructor
//@NoArgsConstructor
@Service
public class LoadUserService implements UserDetailsService {
    //private final UserService  userService;
    private UserRepository userRepository;

    /*public LoadUserService(//UserService userService,
                           UserRepository userRepository) {
        //this.userService = userService;
        this.userRepository = userRepository;
    }*/
    /*public User loadUserSecurely(String username) {
        return (User) userService.loadUserByUsername(username);
    }*/

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByUsername(username);
    }
}
