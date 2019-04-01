package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/", "/css/**", "/js/**", "/images/**").permitAll()		// Rutas sin autorización
			.antMatchers("/user/**").hasAnyRole("USER")								// Rutas solo accesibles para roles USER
			.antMatchers("/admin/**").hasAnyRole("ADMIN")							// Rutas solo accesibles para roles ADMIN
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login").permitAll()							// Login, acceso para todos
			.and()
			.logout().permitAll();													// Logout, acceso para todos
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
