package com.flexisaf.week6_depen_injection.repository;

import com.flexisaf.week6_depen_injection.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
