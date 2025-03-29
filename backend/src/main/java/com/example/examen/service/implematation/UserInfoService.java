package com.example.examen.service.implematation;

import com.example.examen.Repository.UserRepository;
import com.example.examen.entity.User;
import com.example.examen.entity.UserInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDetail = usersRepository.findByUsername(username);

        return userDetail.map(UserInfoDetails::new).orElseThrow(()->{
            throw new RuntimeException("User not found with username "+  username);
        });
    }
}
