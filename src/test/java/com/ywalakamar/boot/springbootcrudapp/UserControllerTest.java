package com.ywalakamar.boot.springbootcrudapp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ywalakamar.boot.controller.UserController;
import com.ywalakamar.boot.model.User;
import com.ywalakamar.boot.repository.UserRepository;
import com.ywalakamar.boot.service.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ContextConfiguration
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @Autowired
    MockMvc mock;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private UserService service;

    @MockBean
    private UserRepository repository;

    @Test
    public void testGetWelcomeMessage() throws Exception {
        mock.perform(get("/")).andExpect(status().isOk());

    }

    @Test
    public void testGetAllUsersMethod() throws Exception {
        List<User> users = new ArrayList<>();
        User user = new User("testFirstName", "testLastName", "testEmail", "testPassword");
        users.add(user);
        when(service.readAll()).thenReturn(users);
        mock.perform(get("/api/v1/users")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].firstName").value("testFirstName"));
    }

    @Test
    public void testCreateUserMethod() throws Exception {
        User user = new User(1L, "testFirstName", "testLastName", "testEmail", "testPassword");
        when(service.create(any(User.class))).thenReturn(user);

        String userJSON = mapper.writeValueAsString(user);
        mock.perform(post("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(userJSON))
                .andExpect(status().isCreated()).andDo(print())
                .andExpect(jsonPath("$.firstName").value("testFirstName"))
                .andExpect(jsonPath("$.lastName").value("testLastName"))
                .andExpect(jsonPath("$.email").value("testEmail"))
                .andExpect(jsonPath("$.password").value("testPassword"));
    }

    @Test
    public void testUpdateUserMethod() throws Exception {
        User user = new User(1L, "Kipruto", "Barno", "barno@mail.com", "password");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("doe@gmail.com");
        user.setPassword("doePassword");
        given(service.update(anyInt(), any(User.class))).willReturn(user);

        /** Jsonify user data */
        String userJsonData = mapper.writeValueAsString(user);

        mock.perform(
                put("/api/v1/users/{id}", user.getId()).contentType(MediaType.APPLICATION_JSON).content(userJsonData))
                .andExpect(status().isOk());
    }
}
