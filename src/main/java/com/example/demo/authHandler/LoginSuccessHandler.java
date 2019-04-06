package com.example.demo.authHandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

// Al anotar con component, la clase se inyecta (como beans) en el contexto de Spring

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
		FlashMap flashMap = new FlashMap();
		flashMap.put("success", "Hello " + 
				authentication.getName() + 
				", your session has been started correctly! (from SuccessHandler)");
		flashMapManager.saveOutputFlashMap(flashMap, request, response);
		
		if (authentication != null) {
			logger.info("The user '" + authentication.getName() + "' has been started a session successfully");
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
}
