package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
public class AdminController {
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
    public String listUsers() {
        return "/admin/users";
    }
}
