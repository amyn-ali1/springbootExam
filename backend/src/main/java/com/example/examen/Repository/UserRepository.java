package com.example.examen.Repository;

import com.example.examen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    // Check if any admin exists
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.role = 'ADMIN'")
    boolean existsAdmin();

}
