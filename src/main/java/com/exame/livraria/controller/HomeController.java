package com.exame.livraria.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
class HomeController {
	@GetMapping("/home")
	public String home(Principal principal) {
		return "home";
	}
}
