package com.example.demo.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(@RequestParam(value="error", required=false) String error, 
			Model model, Principal principal, RedirectAttributes flash) {
		if (principal != null) {
			// Ya se inició sesión previamente...
			flash.addFlashAttribute("info", "Sesión ya iniciada previamente");
			return "redirect:/";
		}
		
		if(error != null) {
			model.addAttribute("error", "Login error. User or password incorrect.");
		}
		return "login";
	}
	
}
