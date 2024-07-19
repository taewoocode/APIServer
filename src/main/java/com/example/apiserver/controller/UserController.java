package com.example.apiserver.controller;

import com.example.apiserver.entity.User;
import com.example.apiserver.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
@RestController
@Validated
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Request to get all users");
        List<User> users = userService.findAll();
        return new ResponseEntity<>( users, null, HttpStatus.OK );
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.info("Request to get user with id {}", id);
        User user = userService.findById(id);
        if (user != null) {
            return new ResponseEntity<>( user, null, HttpStatus.OK );
        } else {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND );
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        log.info("Request to create user: {}", user);
        User createdUser = userService.save(user);
        return new ResponseEntity<>( createdUser, null, HttpStatus.CREATED );
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        log.info("Request to update user with id {}: {}", id, user);
        user.setId(id);
        User updatedUser = userService.save(user);
        return new ResponseEntity<>( updatedUser, null, HttpStatus.OK );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Request to delete user with id {}", id);
        userService.deleteById(id);
        return new ResponseEntity<>(null, null, HttpStatus.NO_CONTENT );
    }
}
