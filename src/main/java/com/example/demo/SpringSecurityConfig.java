package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.authHandler.LoginSuccessHandler;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/", "/css/**", "/js/**", "/images/**").permitAll()		// Rutas sin autorización
			.antMatchers("/user/**").hasAnyRole("USER")								// Rutas solo accesibles para roles USER
			.antMatchers("/admin/**").hasAnyRole("ADMIN")							// Rutas solo accesibles para roles ADMIN
			// The authorization for /authTest is defined via annotations...
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.successHandler(successHandler)											// Para pasar un mensaje personalizado al iniciar sesión con éxito
			.loginPage("/login").permitAll()										// Login, acceso para todos
			.and()
			.logout().permitAll()													// Logout, acceso para todos
			.and()
			.exceptionHandling().accessDeniedPage("/error_403");					// Redirección a página de error en caso de acceso denegado 
	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		UserBuilder users = User.builder().passwordEncoder(encoder::encode);
		
		build.inMemoryAuthentication()
			.withUser(users.username("admin").password("admin").roles("ADMIN", "USER"))
			.withUser(users.username("user").password("user").roles("USER"));
	}
	
}
