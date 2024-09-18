package com.example.spring_security_learn_practice.basic;

import static org.springframework.security.config.Customizer.*;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.http.*;
import org.springframework.security.web.*;

@Configuration
public class BasicAuthSecurityConfiguration {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(
				auth -> {
					auth.anyRequest().authenticated();
					});
		http.sessionManagement(
					session -> 
					session.sessionCreationPolicy( //세션정책을 설정함
							SessionCreationPolicy.STATELESS) //세션 사용 안함으로
					);
//		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		
		//csrf 설정을 해제
		http.csrf().disable();
		
		return http.build();
	}
}
