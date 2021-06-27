package com.ywalakamar.boot.controller;

import com.ywalakamar.boot.model.User;
import com.ywalakamar.boot.repository.UserRepository;
import com.ywalakamar.boot.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<?> welcome() {
        try {
            return ResponseEntity.ok("Welcome to my Spring boot CRUD App");
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/v1/users")
    public ResponseEntity<?> getUsers() {
        try {
            return ResponseEntity.ok(service.readAll());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/v1/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            return new ResponseEntity<>(service.create(user), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/api/v1/users{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {

        try {
            if (service.update(id, user) != null) {
                return new ResponseEntity<>(service.update(id, user), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/v1/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        try {
            if (service.readOne(id) != null) {
                service.delete(id);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
