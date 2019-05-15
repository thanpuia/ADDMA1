package com.lalthanpuia.addma1.security;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.User.UserBuilder;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig  extends WebSecurityConfigurerAdapter {
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//		// add our users for in memory authentication
//		
//		UserBuilder users = User.withDefaultPasswordEncoder();
//		
//		auth.inMemoryAuthentication()
//			.withUser(users.username("john").password("test123").roles("EMPLOYEE"));
//	}
//	
//}

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource securityDataSource;
	
	@Override
	protected void configure (AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication().dataSource(securityDataSource);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().and().csrf().disable()
			.authorizeRequests()
//			.antMatchers("/adminShowAll").hasRole("ADMIN")
			.antMatchers("/request/requestRelief").hasAnyRole("ADMIN", "USER")
			.antMatchers("/report/reportIncident").hasAnyRole("ADMIN", "USER")
				.antMatchers("/phoneAuth","/userDetails").permitAll()
				.and()
				.formLogin().loginPage("/login")
				.permitAll().defaultSuccessUrl("/index")
				.and()
				.logout().
				logoutUrl("/logout").
				logoutSuccessUrl("/login")
				;
	}
}




////		.authorizeRequests()
////        .anyRequest()
////        .authenticated()
////        .and()
////    .authorizeRequests()
////        .antMatchers("/index")
////        .permitAll()
////        .and()
////    .formLogin();
//	}
////				
////	@Autowired
////	public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception {
////		
////		auth
////			.inMemoryAuthentication()
////				.withUser("admin").password("{noop}admin").roles("ADMIN")
////				.and()
////				.withUser("Jojo").password("{noop}jojo").roles("USER");
////	}
//}


