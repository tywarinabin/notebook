package com.project.notebook.service;

import com.project.notebook.model.CustomUserDetails;
import com.project.notebook.model.User;
import com.project.notebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(s);
        User foundUser = user.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return new CustomUserDetails(foundUser);
    }
}
