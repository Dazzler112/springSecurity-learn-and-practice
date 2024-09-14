package com.example.spring_security_learn_practice.resources;

import java.util.*;

import org.slf4j.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class TodoResource {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final List<Todo> TODOS_LIST = 
			List.of(new Todo("player", "Learn AWS"),
					new Todo("player", "Get AWS Certified"));

	@GetMapping("/todos")
	public List<Todo> retrieveAllTodos() {
		return TODOS_LIST;
	} //=>200을 반환
	
	@GetMapping("/users/{username}/todos")
	public Todo retrieveTodosForSpecificUser(@PathVariable String username) {
		return TODOS_LIST.get(0);
	} //=> 200을 반환
	
	@PostMapping("/users/{username}/todos")
	public void createTodosForSpecificUser(@PathVariable String username,
										@RequestBody Todo todo) {
		logger.info("Create {} for {}", todo, username);
	} //=> 401을 반환 why? 읽기요청은 허용했지만 post는 왜? CSRF 토큰 필요!
}

record Todo (String username, String description) {}