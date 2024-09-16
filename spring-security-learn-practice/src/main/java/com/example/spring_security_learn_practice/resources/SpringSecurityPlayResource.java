package com.example.spring_security_learn_practice.resources;

import org.springframework.security.web.csrf.*;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.*;

@RestController
public class SpringSecurityPlayResource {

	@GetMapping("/csrf-token")
	public CsrfToken retrieveCsrfToke(HttpServletRequest request) {
		return (CsrfToken) request.getAttribute("_csrf");
	}
}
