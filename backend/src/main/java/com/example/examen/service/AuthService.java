package com.example.examen.service;

import com.example.examen.entity.User;

public interface AuthService {
    public User Login(User user) ;
    User createAdminUser(User adminUser);
}
