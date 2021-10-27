package com.exame.livraria.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
	// return login page
    @GetMapping("/auth/login")
    public String login(Principal principal) {
        return "login";
    }
    
    @PostMapping("/auth/perform_logout")
    public String logout(Principal principal) {
        return "login";
    }
}
