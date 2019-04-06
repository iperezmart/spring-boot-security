package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//@Secured("ROLE_ADMIN")
@Controller
public class AuthTestController {

	@Value("${application.controllers.title}")
	private String title;
	
	@Value("${application.controllers.authTest.message}")
	private String message;
	
	//@Secured("ROLE_ADMIN")
	//@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/authTest/")
	public String index(Model model) {
		model.addAttribute("title", title);
		model.addAttribute("message", message);
		return "authTest";
	}
	
}
