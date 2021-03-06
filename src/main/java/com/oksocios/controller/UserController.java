package com.oksocios.controller;


import com.oksocios.exceptions.ObjectAlreadyExistsException;
import com.oksocios.model.Establishment;
import com.oksocios.model.User;
import com.oksocios.service.EstablishmentService;
import com.oksocios.service.UserService;
import com.oksocios.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final EstablishmentService establishmentService;

    @Autowired
    public UserController(UserService userService, EstablishmentService establishmentService){
        this.userService = userService;
        this.establishmentService = establishmentService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public String registerPost(@ModelAttribute User user, Model model){
        User userResponse;
        try{
            userResponse = userService.registerAdmin(user);
        }catch(ObjectAlreadyExistsException e){
            model.addAttribute("error", true);
            model.addAttribute("message", e.getMessage());
            return "register";
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(userResponse, null, userResponse.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        Establishment establishment = new Establishment();
        establishment.setUser(userResponse);
        model.addAttribute("establishment", establishment);
        model.addAttribute("user", userResponse);
        return "new-establishment";
    }

}
