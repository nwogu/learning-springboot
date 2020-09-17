package com.gabrielnwogu.elevatorSystem.controllers;

import com.gabrielnwogu.elevatorSystem.domains.User;
import com.gabrielnwogu.elevatorSystem.dto.ClientRegistrationDto;
import com.gabrielnwogu.elevatorSystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;


    @ModelAttribute("user")
    public ClientRegistrationDto clientRegistrationDto() {
        return new ClientRegistrationDto();
    }

    @GetMapping("/admin/login")
    public String viewAdminLogin(Model model)
    {
        return "admin_login";
    }

    @GetMapping("/client/login")
    public String viewClientLogin(Model model)
    {
        return "client_login";
    }

    @GetMapping("/client/registration")
    public String viewClientRegistration(Model model)
    {
        return "client_registration";
    }

    @PostMapping("/client/registration")
    public String registerClient(@ModelAttribute("user") @Valid ClientRegistrationDto userDto,
                                      BindingResult result) {

        User existing = userService.userByUserName(userDto.getUsername());

        if (existing != null) {
            result.addError(new ObjectError("username", "Username is taken"));
            return "client_registration";
        }

        if (result.hasErrors()) {
            return "client_registration";
        }

        userService.save(userDto);
        return "redirect:/client/registration?success";
    }



}
