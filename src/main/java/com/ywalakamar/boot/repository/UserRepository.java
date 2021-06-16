package com.ywalakamar.boot.repository;

// import java.util.List;

import com.ywalakamar.boot.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // List<User> findById(int id);
}
