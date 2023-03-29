package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.HashSet;
import java.util.Set;


@Service
public class RoleServiceJpaImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceJpaImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Override
    public void addRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role getRole(Long id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.getRoleByName(name);
    }

    @Override
    public void deleteRole(Role role) {
        roleRepository.delete(role);
    }

    @Override
    public void updateRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Set<Role> getAllRoles() {
        return new HashSet(roleRepository.findAll());
    }
}
