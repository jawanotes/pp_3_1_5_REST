package ru.kata.spring.boot_security.demo.util;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Collections;

/**
 * Роман Дамбуев:
 * Метод firstRun() вынести в отдельный класс Init и положить в пакет util
 */
@Component
public class Init {
    private final UserService userService;
    private final RoleService roleService;

    public Init(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void firstRun() {
        if (roleService.getAllRoles().isEmpty()) {
            Role role = new Role();
            role.setName("ROLE_USER");
            roleService.addRole(role);

            Role role2 = new Role();
            role2.setName("ROLE_ADMIN");
            roleService.addRole(role2);
        }
        if (userService.getAllUsers().isEmpty()) {
            User user = new User();
            user.setUsername("user");
            user.setPassword("123");
            user.setRoles(Collections.singleton(roleService.getRoleByName("ROLE_USER")));
            userService.addUser(user);

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("123");
            admin.setRoles(roleService.getAllRoles());
            userService.addUser(admin);
        }
    }
}
