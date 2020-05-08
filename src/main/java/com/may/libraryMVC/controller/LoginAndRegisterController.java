package com.may.libraryMVC.controller;

import com.may.libraryMVC.model.dto.UserRegistrationDTO;
import com.may.libraryMVC.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import javax.validation.Valid;

@Controller
public class LoginAndRegisterController {

    private final UserService userService;

    public LoginAndRegisterController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDTO userDto() {
        return new UserRegistrationDTO();
    }

    @RequestMapping("login")
    public String login() {

        return "login";
    }


    @RequestMapping("registration")
    public String register() {


        return "registration";
    }



    @RequestMapping(value = "registration", method = RequestMethod.POST)
    public String save(@ModelAttribute("user") @Valid UserRegistrationDTO userDTO, BindingResult result) {

        if (userService.getUserByUsername(userDTO.getUsername()).isPresent()) {
            result.rejectValue("username", null, "There is already an account registered with that username");
        }
        if (userService.getUserByEmail(userDTO.getEmail()).isPresent()) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if(result.hasErrors()){
            return "registration";
        }
        userService.saveOrEditUser(userDTO);
        return "redirect:/registration?success";
    }
}
