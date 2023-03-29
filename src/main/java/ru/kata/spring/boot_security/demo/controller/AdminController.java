package ru.kata.spring.boot_security.demo.controller;


//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.LoadUserService;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
//import java.util.logging.Logger;

@Controller
@RequestMapping("/admin")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserService userService;
    private final LoadUserService loadUserService;
    private final RoleService roleService;
    //Logger logger = Logger.getLogger("logger");

    public AdminController(UserService userService,
                           RoleService roleService,
                           @Qualifier(value = "loadUserServiceProvider")
                           LoadUserService loadUserService) {
        this.userService = userService;
        this.roleService = roleService;
        this.loadUserService = loadUserService;
    }

    @GetMapping("")
    public String index() {
        return "redirect:/admin/users";
    }

    // Requirement 7:
    @GetMapping("/admin")
    public String admin() {
        return "redirect:/admin/users";
    }

    @GetMapping("/users")
    public String listUsers(ModelMap model, Principal principal) {
        User user = (User) loadUserService.loadUserByUsername(principal.getName());
        List<User> userList = (List<User>) userService.getAllUsers();
        model.addAttribute("user", user);
        //logger.info(userList.get(0).getUsername());
        //logger.info(userList.get(0).getRolesAsString());
        model.addAttribute("userlist", userList);
        model.addAttribute("newuser", new User(0L,"", "", new HashSet<Role>()));
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "/admin/users";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam(value = "id", required = true) Long id, ModelMap model) {
        User user = userService.getUser(id);
        user.setPassword("");
        Set<Role> roles = user.getRoles();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("userRoles", roles);
        return "/admin/edit";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, ModelMap model) {
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "/admin/new";
    }

    @PostMapping("/addnew")
    public String createUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/delete")
    public String deleteUser(@ModelAttribute("user") User user) {
        userService.deleteUser(user);
        return "redirect:/admin/users";
    }
}
