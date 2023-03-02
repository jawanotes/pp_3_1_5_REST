package ru.kata.spring.boot_security.demo.controller;


//import com.hkl.pp_3_1_2_crud_boot.model.User;
//import com.hkl.pp_3_1_2_crud_boot.service.UserService;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.LoadUserService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserService userService;
    private final LoadUserService loadUserService;

    public AdminController(UserService userService,
                           @Qualifier(value = "loadUserServiceProvider")
                           LoadUserService loadUserService) {
        this.userService = userService;
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
        //User user = (User) userService.loadUserByUsername(principal.getName());
        User user = loadUserService.loadUserSecurely(principal.getName());
        List<User> userList = (List<User>) userService.getAllUsers();
        model.addAttribute("user", user);
        model.addAttribute("userlist", userList);
        return "/admin/users";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam(value = "id", required = true) Long id, ModelMap model) {
        User user = userService.getUser(id);
        user.setPassword("");
        Set<Role> roles = user.getRoles();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", userService.getAllRoles());
        model.addAttribute("userRoles", roles);
        return "/admin/edit";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user, ModelMap model) {
        model.addAttribute("allRoles", userService.getAllRoles());
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
