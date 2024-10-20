package com.flexisaf.week6_depen_injection.controller;

import com.flexisaf.week6_depen_injection.dto.UserDto;
import com.flexisaf.week6_depen_injection.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {


    @Autowired
    // Field injection
    private UserService userService;

    // Constructor injection (preferred for required dependencies)
    public UserController(UserService userService) {
        this.userService = userService; // Injected through constructor
    }

    // Setter injection
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    //private final UserService userService; // week 6- dependency injection (flexisaf)

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        UserDto savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId) {
        UserDto user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId, @RequestBody @Valid UserDto user) {
        user.setId(userId);
        UserDto updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
    }
}
