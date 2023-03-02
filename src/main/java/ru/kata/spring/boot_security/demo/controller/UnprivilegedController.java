package ru.kata.spring.boot_security.demo.controller;

//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.LoadUserService;
//import ru.kata.spring.boot_security.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
//@PreAuthorize("hasRole('USER')")
public class UnprivilegedController {
    //private final UserService userService;
    private final LoadUserService loadUserService;

    public UnprivilegedController(//UserService userService,
                                  @Qualifier(value = "loadUserServiceProvider")
                                  LoadUserService loadUserService) {
        //this.userService = userService;
        this.loadUserService = loadUserService;
    }
    @GetMapping("")
    public String index() {
        return "index";
    }
    @GetMapping("/user")
    public String userPage(Principal principal, ModelMap model) {
        //User user = (User) userService.loadUserByUsername(principal.getName());
        User user = (User) loadUserService.loadUserByUsername(principal.getName());
        model.addAttribute("user", user);
        /*if (user.getRoles().contains("ROLE_ADMIN")) {
            model.addAttribute("isAdmin", true);
        }*/
        return "/user/user";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }
        return "/";
    }
}
