package com.ywalakamar.crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ywalakamar.crud.dto.UserRequest;
import com.ywalakamar.crud.exceptions.MissingArgumentException;
import com.ywalakamar.crud.exceptions.RecordNotFoundException;
import com.ywalakamar.crud.model.User;
import com.ywalakamar.crud.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository usersDB;

    public User create(UserRequest req) {
        User user = User.build(0, req.getFirstName(), req.getLastName(), req.getEmail() != null ? req.getEmail() : null,
                req.getPassword());
        return usersDB.save(user);
    }

    public List<User> getAll() {
        return usersDB.findAll();
    }

    public User findUserById(int id) {
        if (id == 0) {
            throw new MissingArgumentException("Id must be specified!");
        }
        if (!usersDB.findById(id).isPresent()) {
            throw new RecordNotFoundException("User with id " + id + " not found!");
        }
        return usersDB.findById(id).get();
    }

    public User findUserByUsername(String username) {
        if (username == null) {
            throw new MissingArgumentException("Username must be specified!");

        }
        if (!usersDB.findByUsername(username).isPresent()) {
            throw new RecordNotFoundException("User with id " + username + " not found!");
        }
        return usersDB.findByUsername(username).get();

    }

    public User update(int id, UserRequest user) {
        try {
            Optional<User> dbRecord = usersDB.findById(id);
            if (dbRecord.isPresent()) {
                User dbUser = dbRecord.get();
                dbUser.setFirstName(user.getFirstName());
                dbUser.setLastName(user.getLastName());
                dbUser.setEmail(user.getEmail());
                dbUser.setPassword(user.getEmail());
                return usersDB.save(dbUser);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void delete(int id) {
        if (id == 0) {
            throw new MissingArgumentException("Id must be specified!");
        }
        if (!usersDB.findById(id).isPresent()) {
            throw new RecordNotFoundException("User with id " + id + " not found!");
        }
        usersDB.deleteById(id);
    }

}