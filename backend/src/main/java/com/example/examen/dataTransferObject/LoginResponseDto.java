package com.example.examen.dataTransferObject;

import com.example.examen.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data

public class LoginResponseDto implements Serializable {
    private String token;
    private User user;
    private String message;

    public LoginResponseDto(String token, User user, String message) {
        this.token = token;
        this.user = user;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}