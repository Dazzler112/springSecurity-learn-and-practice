package com.example.spring_security_learn_practice.basic;

import static org.springframework.security.config.Customizer.*;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.http.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.*;
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
	
	//🔵 InMemory 방식
	@Bean
	public UserDetailsService userDetailsService() {
		
		var user = User.withUsername("player") //사용자 이름은 player로 설정
			.password("{noop}dummy")//아직 인코딩하지 않을거여서 noop를 사용하고 패스워드를 dummy로 하겠다
			.roles("USER") //역할은 USER
			.build();
		
		var admin = User.withUsername("admin") //사용자 이름은 admin로 설정
				.password("{noop}dummy")//패스워드는 위와 동일하게
				.roles("ADMIN") //역할은 ADMIN
				.build();
			
		return new InMemoryUserDetailsManager(user, admin); //이곳에 user,admin을 입력
	}
}
