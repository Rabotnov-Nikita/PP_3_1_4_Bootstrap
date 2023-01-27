package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    private UserValidator userValidator;
    private UserService userService;
    private RoleService roleService;

    public MainController(UserValidator userValidator, UserService userService, RoleService roleService) {
        this.userValidator = userValidator;
        this.userService = userService;
        this.roleService = roleService;
    }

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

    @GetMapping("/admin_page")
    public String adminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "auth/admin_page";
    }

    @GetMapping("/user")
    public String pageForUser(Principal principal, Model model) {
        User user = userService.getUserByUserName(principal.getName()).get();
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "auth/new";
    }

    @PostMapping("/admin_page")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.getAllRoles());
            return "auth/new";
        }
        userService.addUser(user);
        return "redirect:/admin_page";
    }

    @DeleteMapping("/admin_page/{username}")
    public String deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return "redirect:/admin_page";
    }

    @GetMapping("admin_page/{username}")
    public String editUser(@PathVariable("username") String username, Model model) {

        model.addAttribute("user", userService.getUserByUserName(username).get());
        model.addAttribute("roles", roleService.getAllRoles());
        return "/auth/edit";

    }
    @PatchMapping("/admin_page/{username}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("username") String username) {
        userService.updateUser(username, user);
        return "redirect:/admin_page";
    }

}
