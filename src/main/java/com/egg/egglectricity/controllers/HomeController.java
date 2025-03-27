package com.egg.egglectricity.controllers;

import com.egg.egglectricity.entities.UserImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping
    public String index() {
        return "index.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/home")
    public String home(HttpSession session) {
        UserImpl loggedUser = (UserImpl) session.getAttribute("userSession");
        return "home.html";
    }
}


