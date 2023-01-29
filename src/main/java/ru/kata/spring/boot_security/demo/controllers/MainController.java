package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @GetMapping("/")
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm simple Spring Security application");
        messages.add("Introducing Users model");
        model.addAttribute("messages", messages);
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";

    }

}
