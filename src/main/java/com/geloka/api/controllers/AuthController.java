package com.geloka.api.controllers;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geloka.api.services.UserService;

import io.swagger.annotations.Api;

//@Api("oauth")
@RestController
@RequestMapping("auth")
@Api(tags="Authentications actions")
public class AuthController {

	private UserService userService;
	private BCryptPasswordEncoder passwordEncoder;
	private DefaultTokenServices tokenServices;
	
	public AuthController(
			UserService userService, 
			BCryptPasswordEncoder passwordEncoder,
			DefaultTokenServices tokenServices) {
		// TODO Auto-generated constructor stub
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.tokenServices = tokenServices;
	}
	
	@GetMapping("/test")
	public String test() {
		return "Geloka API is working";
	}

}

