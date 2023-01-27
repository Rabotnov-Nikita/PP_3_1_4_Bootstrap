package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public Optional<User> getUserByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Transactional
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String userName) {
        userRepository.delete(userRepository.findByUsername(userName).get());
    }

    @Transactional
    public void updateUser(String username, User user) {
        User updatedUser = userRepository.findByUsername(username).get();
        if (!user.getFirstName().equals("")) updatedUser.setFirstName(user.getFirstName());
        if (!user.getLastName().equals("")) updatedUser.setLastName(user.getLastName());
        if (user.getAge() != null) updatedUser.setAge(user.getAge());
        if (!user.getUsername().equals("")) updatedUser.setUsername(user.getUsername());
        if (!user.getPassword().equals("")) updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        if (!user.getRoles().isEmpty()) updatedUser.setRoles(user.getRoles());
        userRepository.saveAndFlush(updatedUser);
    }

}
