package com.example.demo.controllers;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
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

	private boolean hasRole(String role) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext == null) {
			return false;
		}
		
		Authentication auth = securityContext.getAuthentication();
		if (auth == null) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		return authorities.contains(new SimpleGrantedAuthority(role));
		
		// Otra forma de hacerlo, recorriendo la colección con un for
//		for (GrantedAuthority authority : authorities) {
//			if (role.equals(authority.getAuthority())) {
//				return true;
//			}
//		}
//		
//		return false;
	}
	
	@GetMapping("/")
	public String index(Model model, Authentication authentication, HttpServletRequest request) {
		// User authentication check - Authentication
		if (authentication != null) {
			logger.info("[FROM Authentication injectable] User authenticated: '" + authentication.getName() + "'");
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			logger.info("[FROM SecurityContextHolder] User authenticated: '" + auth.getName() + "'");
		}
		
		if (hasRole("ADMIN") == true) {
			logger.info("[CHECK ROLE] User has access, your role is 'ADMIN'");
		}
		else {
			logger.info("[CHECK ROLE] Your role is not 'ADMIN'");
		}
		
		// User authentication check - SecurityContextHolderAwareRequestWrapper
		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
		if (securityContext.isUserInRole("ADMIN")) {
			logger.info("[FROM SecurityContextHolderAwareRequestWrapper] User authenticated: '" + authentication.getName() + "'");
		}
		
		// User authentication check - HttpServletRequest
		if (request.isUserInRole("ROLE_ADMIN")) {
			logger.info("[FROM HttpServletRequest] User authenticated: '" + authentication.getName() + "'");
		}
		
		model.addAttribute("title", title);
		model.addAttribute("message", message);
		return "index";
	}
	
}
