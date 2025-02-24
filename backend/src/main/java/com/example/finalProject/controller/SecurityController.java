package com.example.finalProject.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityController {
	
	@GetMapping("/username")
	@ResponseBody
	public String currentUserName(Principal principal) {
		System.out.println("username ::: " + principal.getName());
		return principal.getName();
	}


}

