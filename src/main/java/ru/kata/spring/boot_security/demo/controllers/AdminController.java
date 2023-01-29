package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin_page")
public class AdminController {

    private UserValidator userValidator;
    private UserService userService;
    private RoleService roleService;

    public AdminController(UserValidator userValidator, UserService userService, RoleService roleService) {
        this.userValidator = userValidator;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String adminPage(@AuthenticationPrincipal UserDetails user, Model model, Principal principal) {
        model.addAttribute("currentUser", userService.getUserByUserName(user.getUsername()));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "auth/admin_page_";
    }


    @GetMapping("/newUser")
    public String newUser(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("currentUser", userService.getUserByUserName(user.getUsername()));
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "auth/new_";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User newUser, BindingResult bindingResult, Model model, @AuthenticationPrincipal UserDetails user) {
        userValidator.validate(newUser, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("currentUser", userService.getUserByUserName(user.getUsername()));
            model.addAttribute("roles", roleService.getAllRoles());
            return "auth/new_";
        }
        userService.addUser(newUser);
        return "redirect:/admin_page";
    }

    @DeleteMapping("/{username}")
    public String deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return "redirect:/admin_page";
    }

    @GetMapping("/{username}")
    public String editUser(@PathVariable("username") String username, Model model) {

        model.addAttribute("user", userService.getUserByUserName(username).get());
        model.addAttribute("roles", roleService.getAllRoles());
        return "/auth/edit";

    }
    @PatchMapping("/{username}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("username") String username) {
        userService.updateUser(username, user);
        return "redirect:/admin_page";
    }


}
