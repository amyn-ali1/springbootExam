package com.example.examen.service;


import com.example.examen.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService {
    User save(User user);
}
