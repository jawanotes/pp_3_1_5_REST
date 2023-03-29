package ru.kata.spring.boot_security.demo.service;


import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceJpaImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserServiceJpaImpl(UserRepository userRepository,
                              PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    /**
     * в сервисе вот тут смешной момент (отредактировано)
     * + у тебя есть риск циклических зависимостей
     * а ну ты костылем решил
     *
     * Harin Konstantin: 1 Transactional убрал
     */
    //@Transactional(readOnly = true)
/*    @Override
    public User getUser(User user) {
        return getUser(user.getId());
    }*/

    @Transactional(readOnly = true)
    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByUsername(username);
    }
}
