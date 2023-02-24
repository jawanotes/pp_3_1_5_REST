package ru.kata.spring.boot_security.demo.service;


//import com.hkl.pp_3_1_2_crud_boot.dao.UserDao;
//import com.hkl.pp_3_1_2_crud_boot.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.List;
@Primary
@Service
public class UserServiceJpaImpl implements UserService {
    //private final UserDao userDao;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private PasswordEncoder encoder;

    public UserServiceJpaImpl(//UserDao userDao,
                              UserRepository userRepository,
                              RoleRepository roleRepository,
                              PasswordEncoder encoder) {
        //this.userDao = userDao;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        //userDao.addUser(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUser(User user) {
        return getUser(user.getId());
        //return userRepository.getReferenceById(user.getId());
        //return userDao.getUser(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).get();
        //return userRepository.getReferenceById(id);
        //return userDao.getUser(id);
    }

    /*    @Override
        public void deleteUser(long id) {
            userDao.deleteUser(id);
        }*/
    @Transactional
    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
        //userDao.deleteUser(user);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        /*user.setPassword();
        userDao.updateUser(user);*/
    }

    @Override
    @Transactional(readOnly = true)
    public List<?> getAllUsers() {
        return userRepository.findAll();
        //return userDao.getAllUsers();
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        return user;
    }

    @PostConstruct
    private void firstRun() {
        if (roleRepository.count() == 0) {
            Role role = new Role();
            role.setName("ROLE_USER");
            roleRepository.save(role);

            Role role2 = new Role();
            role2.setName("ROLE_ADMIN");
            roleRepository.save(role2);
        }
        if (getAllUsers().isEmpty()) {
            //Role userRole = roleRepository.getRoleByName("ROLE_USER");
            //List<Role> roles = List.of(userRole);
            User user = new User();
            user.setUsername("user");
            user.setPassword("123");
            //user.setRoles(List.of(userRole));
            user.setRoles(List.of(roleRepository.getRoleByName("ROLE_USER")));
            addUser(user);

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("123");
            admin.setRoles(roleRepository.findAll());
            addUser(admin);
        }
    }
}
