package com.ywalakamar.boot.springbootcrudapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ywalakamar.boot.model.User;
import com.ywalakamar.boot.repository.UserRepository;
import com.ywalakamar.boot.service.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    UserService service;

    @Test
    public void testSaveUser() {
        User user = new User(0, "Kipruto", "Barno", "barno@mail.com", "password");
        when(repository.save(any(User.class))).thenReturn(user);
        User created = service.create(user);
        assertEquals(created.getEmail(), user.getEmail());
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();

        User user = new User(0, "Kipruto", "Barno", "barno@mail.com", "password");
        users.add(user);
        when(repository.findAll()).thenReturn(users);
        List<User> expectedList = service.readAll();
        assertEquals(expectedList, users);
    }

    @Test
    public void testGetOneUser() {
        User user = new User(0, "Kipruto", "Barno", "barno@mail.com", "password");
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        Optional<User> expected = service.readOne(user.getId());
        assertEquals(expected.get(), user);
    }

    @Test
    public void testUpdateUser() {
        User user = new User(0, "Kipruto", "Barno", "barno@mail.com", "password");
        User newUser = new User("John", "Doe", "doe@mail.com", "doepassword");
        when(repository.findById(user.getId())).thenReturn(Optional.of(newUser));
        service.update(user.getId(), newUser);
        verify(repository).save(newUser);

    }

    @Test
    public void testDeleteUser() {
        User user = new User(0, "Kipruto", "Barno", "barno@mail.com", "password");
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        service.delete(user.getId());
        verify(repository).deleteById(user.getId());
    }

}
