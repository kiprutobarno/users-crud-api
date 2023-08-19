package com.ywalakamar.crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ywalakamar.crud.converters.UserConverter;
import com.ywalakamar.crud.dto.UserDto;
import com.ywalakamar.crud.exceptions.MissingArgumentException;
import com.ywalakamar.crud.exceptions.RecordNotFoundException;
import com.ywalakamar.crud.model.User;
import com.ywalakamar.crud.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository usersDB;

    @Autowired
    private UserConverter converter;

    public UserDto create(UserDto userDTO) {
        User user = converter.dtoToEntity(userDTO);
        user = usersDB.save(user);
        return converter.entityToDto(user);
    }

    public List<UserDto> getAll() {
        List<User> users = usersDB.findAll();
        return converter.entitiesToDtos(users);
    }

    public UserDto findUserById(int id) {
        if (id == 0) {
            throw new MissingArgumentException("Id must be specified!");
        }
        if (!usersDB.findById(id).isPresent()) {
            throw new RecordNotFoundException("User with id " + id + " not found!");
        }
        // Get user from repository
        User user = usersDB.findById(id).get();

        // Convert retrieved user to DTO
        return converter.entityToDto(user);

    }

    public UserDto update(int id, UserDto userDto) {
        User update = converter.dtoToEntity(userDto);
        try {
            Optional<User> dbRecord = usersDB.findById(id);
            if (dbRecord.isPresent()) {
                User dbUser = dbRecord.get();

                dbUser.setFirstName(update.getFirstName());
                dbUser.setLastName(update.getLastName());
                dbUser.setEmail(update.getEmail());
                dbUser.setPassword(update.getPassword());
                usersDB.save(dbUser);
                log.info("SAVE USER:" + usersDB.save(dbUser));
                return converter.entityToDto(dbUser);
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