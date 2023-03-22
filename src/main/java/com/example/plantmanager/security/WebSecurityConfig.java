package com.example.plantmanager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.plantmanager.security.jwt.JwtAuthenticationEntryPoint;
import com.example.plantmanager.security.jwt.JwtAuthenticationTokenFilter;
import com.example.plantmanager.service.impl.UserDetailsServiceImpl;

/**
* Configuration of security related beans and methods. The access to different
* urls within the application is defined here.
* 
* <ul>
* <li>{@link EnableWebSecurity} allows Spring to find and automatically apply the 
* class to the global Web Security.</li>
* <li>{@link EnableGlobalMethodSecurity} provides AOP security on methods. It enables 
* @PreAuthorize, @PostAuthorize, it also supports JSR-250.</li>
* </ul>
*
* @author Nandha Kumar
* @version 1.0
* @since 14-03-2023
* 
*/

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
	
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	/**
	 * This function used configure CORS and CSRF,when we want to require all users to 
	 * be authenticated or not, which filter (JwtAuthenticationTokenFilter) and when we
	 * want it to work (filter before UsernamePasswordAuthenticationFilter), which Exception
	 * Handler is chosen (JwtAuthenticationEntryPoint).
	 * 
	 * @param http
	 * @return
	 * @throws Exception
	 */
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors().and()
			.csrf().disable()
			.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
			.antMatchers("/api/auth/**").permitAll()
			.anyRequest().authenticated();
		
		http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
		return new JwtAuthenticationTokenFilter();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
}
