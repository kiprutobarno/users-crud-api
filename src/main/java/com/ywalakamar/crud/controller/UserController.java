package com.ywalakamar.crud.controller;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import com.ywalakamar.crud.dto.UserDto;
import com.ywalakamar.crud.repository.UserRepository;
import com.ywalakamar.crud.response.Response;
import com.ywalakamar.crud.services.UserService;
import com.ywalakamar.crud.utils.StatusCode;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/api/v1/users")
    public Response getUsers() {

        List<UserDto> users = service.getAll();
        return new Response(true, 200, "Success", users);
    }

    @GetMapping("/api/v1/users/{id}")
    public Response getUserById(@PathVariable("id") int id) {
        UserDto user = service.findUserById(id);
        return new Response(true, StatusCode.SUCCESS, "Success", user);
    }

    @PostMapping("/api/v1/users")
    public Response createUser(@Valid @RequestBody UserDto userDto) {
        return new Response(true, 201, "Success", service.create(userDto));
    }

    @PutMapping("/api/v1/users/{id}")
    public Response updateUser(@PathVariable("id") int id, @Valid @RequestBody UserDto user) {
        return new Response(true, StatusCode.SUCCESS, "Success", service.update(id, user));
    }

    @DeleteMapping("/api/v1/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        try {
            if (service.findUserById(id) != null) {
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
