package com.ywalakamar.boot.service;

import java.util.List;
import java.util.Optional;

import com.ywalakamar.boot.model.User;
import com.ywalakamar.boot.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public UserService(UserRepository repo) {
        this.repository = repo;
    }

    /**
     * Creates a user entry in the database table;
     * 
     * @param user {@link User} {@link Object}
     * @return the saved {@link User} object
     */

    public User create(User user) {
        try {
            user = new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
            return repository.save(user);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * Fetches all users saved in the database table
     * 
     * @return a List of users
     */

    public List<User> readAll() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }

    /**
     * Fetches user record as saved in the database
     * 
     * @param id the Id of the user in the database table
     * @return {@link User} object
     */
    public Optional<User> readOne(long id) {
        try {
            Optional<User> data = repository.findById(id);
            if (data.isPresent()) {
                return repository.findById(id);
            } else {
                return null;
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * Updates a user record in the database table
     * 
     * @param id   Id of the user
     * @param user {@link User} object data to be updated
     * @return user {@link Object}
     */

    public User update(long id, User user) {
        try {
            if (repository.findById(id) != null) {
                User usr = new User();
                usr.setFirstName(user.getFirstName());
                usr.setLastName(user.getLastName());
                usr.setEmail(user.getEmail());
                usr.setPassword(user.getEmail());
                return repository.save(usr);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * Deletes user record as stored in the
     * {@link org.hibernate.boot.model.relational.Database}
     * 
     * @param id he Id of the user to be deleted in the database table
     */

    public void delete(long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}