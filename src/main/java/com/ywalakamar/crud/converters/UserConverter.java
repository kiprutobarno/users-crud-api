package com.ywalakamar.crud.converters;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ywalakamar.crud.dto.UserDto;
import com.ywalakamar.crud.model.User;

@Component
public class UserConverter {
    public UserDto entityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public List<UserDto> entitiesToDtos(List<User> users) {
        return users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
    }

    public User dtoToEntity(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public List<User> dtosToEntities(List<UserDto> userDtos) {
        return userDtos.stream().map(dto -> dtoToEntity(dto)).collect(Collectors.toList());
    }
}
