package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

/**
 * Роман Дамбуев:
 * Добавить для сущности role сервис слой
 */
public interface RoleService {
    void addRole(Role role);
    Role getRole(Long id);
    Role getRoleByName(String name);
    void deleteRole(Role role);
    void updateRole(Role role);
    Set<Role> getAllRoles();
}
