package com.example.spring_security_learn_practice.resources;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloWorldResource {

	@GetMapping("/hello-world")
	public String helloWorld() {
		return "Hello World v1";
	}
}
