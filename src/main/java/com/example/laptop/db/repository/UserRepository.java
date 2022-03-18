package com.example.laptop.db.repository;

import com.example.laptop.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndActiveTrue(String email);

    Optional<User> findByIdAndActiveTrue(Long id);
}
