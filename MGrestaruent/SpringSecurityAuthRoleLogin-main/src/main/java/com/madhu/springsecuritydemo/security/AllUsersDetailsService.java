package com.madhu.springsecuritydemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.madhu.springsecuritydemo.model.AllUsers;
import com.madhu.springsecuritydemo.repository.AllUsersRepository;

@Service
public class AllUsersDetailsService implements UserDetailsService {

    @Autowired
    private AllUsersRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AllUsers user = repository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .roles(user.getRole()) // e.g. "ADMIN" or "USER"
            .build();
    }
}