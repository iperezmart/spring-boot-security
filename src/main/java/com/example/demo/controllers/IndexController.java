package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@Value("${application.controllers.title}")
	private String title;
	
	@Value("${application.controllers.message}")
	private String message;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("title", title);
		model.addAttribute("message", message);
		return "index";
	}
	
}
