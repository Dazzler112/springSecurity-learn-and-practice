package com.example.spring_security_learn_practice.basic;

import static org.springframework.security.config.Customizer.*;

import javax.sql.*;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.embedded.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.http.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.jdbc.*;
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
					session.sessionCreationPolicy( 
							SessionCreationPolicy.STATELESS)
					);
//		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		
		http.csrf().disable();
		
		http.headers().frameOptions().sameOrigin(); //요청이 동일한 오리진에서 오는 경우 해당 애플리케이션에 프레임을 허용하도록 지정
		
		return http.build();
	}
	

//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		var user = User.withUsername("player")
//			.password("{noop}dummy")
//			.roles("USER")
//			.build();
//		
//		var admin = User.withUsername("admin") 
//				.password("{noop}dummy")
//				.roles("ADMIN") 
//				.build();
//			
//		return new InMemoryUserDetailsManager(user, admin);
//	}
	
	@Bean // 해당 빈 생성으로 DB 방식으로 교체 (H2, JDBC)
	public DataSource dataSource() {
		
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
				.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
		
		var user = User.withUsername("player")
			.password("{noop}dummy")
			.roles("USER")
			.build();
		
		var admin = User.withUsername("admin") 
				.password("{noop}dummy")
				.roles("ADMIN", "USER") 
				.build();
		
		//JDBC 방식으로 유저 저장
		var jdbcUserDetailManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailManager.createUser(user);	
		jdbcUserDetailManager.createUser(admin);	
		
		return jdbcUserDetailManager;
	}
}
