package com.ywalakamar.crud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ywalakamar.crud.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * from users u where u.username = ?1", nativeQuery = true)
    Optional<User> findByUsername(String username);
}
