package com.example.demo.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Value("${application.controllers.title}")
	private String title;
	
	@Value("${application.controllers.message}")
	private String message;

	@GetMapping("/")
	public String index(Model model, Authentication authentication) {
		if (authentication != null) {
			logger.info("[FROM Authentication injectable] User authenticated: '" + authentication.getName() + "'");
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			logger.info("[FROM SecurityContextHolder] User authenticated: '" + authentication.getName() + "'");
		}
		
		model.addAttribute("title", title);
		model.addAttribute("message", message);
		return "index";
	}
	
}
