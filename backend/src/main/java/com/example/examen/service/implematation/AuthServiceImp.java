package com.example.examen.service.implematation;

import com.example.examen.Repository.UserRepository;
import com.example.examen.entity.User;
import com.example.examen.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImp implements AuthService {

    private final UserRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImp(UserRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User Login(User user) {
        String username = user.getUsername();
        User userTemp = this.usersRepository.findByUsername(username).orElseThrow(()->{
            throw new RuntimeException("User not found with user name "+ username);
        });

        if (passwordEncoder.matches(user.getPassword(), userTemp.getPassword())) {
            // Passwords match, authentication successful
            return userTemp;
        } else {
            // Passwords don't match, authentication failed
            throw new RuntimeException("Invalid credentials");
        }
    }

    @Override
    public User createAdminUser(User adminUser) {
        // Check if admin already exists
        if (usersRepository.existsAdmin()) {
            throw new IllegalStateException("Admin user already exists in database");
        }

        // Validate input
        if (adminUser.getUsername() == null || adminUser.getPassword() == null) {
            throw new IllegalArgumentException("Username and password are required");
        }

        // Check for existing username
        usersRepository.findByUsername(adminUser.getUsername()).ifPresent(user -> {
            throw new IllegalArgumentException("Username already exists");
        });

        // Set admin role and encode password
        adminUser.setRole("ADMIN");
        adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));

        // Save and return the new admin user
        return usersRepository.save(adminUser);
    }
}
