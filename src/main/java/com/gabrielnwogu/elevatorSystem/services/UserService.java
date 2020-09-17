package com.gabrielnwogu.elevatorSystem.services;

import com.gabrielnwogu.elevatorSystem.domains.Client;
import com.gabrielnwogu.elevatorSystem.domains.User;
import com.gabrielnwogu.elevatorSystem.dto.ClientRegistrationDto;
import com.gabrielnwogu.elevatorSystem.repositories.ClientRepository;
import com.gabrielnwogu.elevatorSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User userByUserName(String username)
    {
        return userRepository.findByUsername(username);
    }

    public User save(ClientRegistrationDto registration) {
        User user = new User();
        user.setUsername(registration.getUsername());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setRole("client");
        userRepository.save(user);

        Client client = new Client();

        client.setName(registration.getName());
        client.setAddress(registration.getAddress());
        client.setEmail(registration.getEmail());
        client.setCompanyName(registration.getCompanyName());
        client.setContactDetails(registration.getContactDetails());
        client.setUser(user);
        clientRepository.save(client);

        return user;
    }
}
