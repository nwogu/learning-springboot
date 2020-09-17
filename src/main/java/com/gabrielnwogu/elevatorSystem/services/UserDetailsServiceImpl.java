package com.gabrielnwogu.elevatorSystem.services;

import com.gabrielnwogu.elevatorSystem.domains.User;
import com.gabrielnwogu.elevatorSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameAndRole(userName, "client");

        if (user == null) {
            throw new UsernameNotFoundException("User name not found");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password( user.getPassword() )
                .roles(user.getRole())
                .build();
    }

    public void save()
    {

    }
}
