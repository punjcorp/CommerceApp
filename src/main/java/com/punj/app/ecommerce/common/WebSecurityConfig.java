/**
 * 
 */
package com.punj.app.ecommerce.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.punj.app.ecommerce.services.UserService;
import com.punj.app.ecommerce.utils.Utils;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Autowired
	private UserService userService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/admin/**").hasAnyRole("ADMIN","CASHIER")
		.and().formLogin()  //login configuration
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/home")	
		.and().logout()    //logout configuration
		.logoutUrl("/logout") 
		.logoutSuccessUrl("/login")
		.and().exceptionHandling() //exception handling configuration
		.accessDeniedPage("/403");
		
		http
		   .headers()
		      .frameOptions()
		         .sameOrigin();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(Utils.getPassEncoder());
		auth.eraseCredentials(false);

	}
}