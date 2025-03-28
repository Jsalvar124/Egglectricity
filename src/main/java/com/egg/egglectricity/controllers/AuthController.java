package com.egg.egglectricity.controllers;

import com.egg.egglectricity.exceptions.InvalidInputException;
import com.egg.egglectricity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap model) {
        if (error != null) {
            model.put("error", "Invalid credentials!");
        }
        return "login-form.html";
    }

    @GetMapping("/register")
    public String register() {
        return "register-form.html";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String lastName, @RequestParam String email, @RequestParam String password, @RequestParam String repeatPassword, ModelMap model)  {
        try{
            userService.register(name, lastName, email, password, repeatPassword);
            model.put("success", "User registration successful!");
            return "redirect:/auth/login";
        }catch (InvalidInputException e) {
            model.put("error", e.getMessage());
            model.put("name", name);
            model.put("email", email);
            model.put("lastName", lastName);
            model.put("password", password);
            return "register-form.html";
        }
    }
}
