package com.example.examen.controller;

import com.example.examen.dataTransferObject.LoginResponseDto;
import com.example.examen.entity.User;
import com.example.examen.service.AuthService;
import com.example.examen.service.implematation.JwtService;
import com.example.examen.service.implematation.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/auth", produces = {MediaType.APPLICATION_JSON_VALUE})
@CrossOrigin(origins = "*")
public class AuthController {
    private AuthService authService;
    private JwtService jwtService;
    private UserInfoService userDetailsService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthService authService, JwtService jwtService, AuthenticationManager authenticationManager
            , UserInfoService userDetailsService){
        this.authService = authService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody User user){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        String token;
        if (authentication.isAuthenticated()) {
            token = jwtService.generateToken(user.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDto(token, user,"login successfully"));
    }

    @PostMapping("/create-admin")
    public User createAdminUser(@RequestBody User adminUser) {
        // This ensures the endpoint can only be called when NO ONE is logged in
        return authService.createAdminUser(adminUser);
    }
}
