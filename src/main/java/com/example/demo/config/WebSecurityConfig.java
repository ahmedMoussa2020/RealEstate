package com.example.demo.config;


import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.example.demo.filter.CustomAuthEntryPoint;
import com.example.demo.filter.JwtAuthorizationFilter;
import com.example.demo.provider.ResourceProvider;


//  The @Configuration annotation indicates that this class contains Spring configuration
@Configuration

//  @EnableWebSecurity enables the Spring Security configuration.
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	ResourceProvider provider;

	@Autowired
	JwtAuthorizationFilter jwtAuthorizationFilter;

	@Autowired
	CustomAuthEntryPoint customAuthEntryPoint;

	// signature to indicate that this method will create a bean for dependency injection in the Spring context.
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}



	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	    return authenticationConfiguration.getAuthenticationManager();
	}

	// MvcRequestMatcher.Builder: is a class related to Spring Security that helps in defining request matchers for security configurations. It's used to create a request matcher based on the Spring MVC framework.
	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
			return new MvcRequestMatcher.Builder(introspector);
	}

	// This method takes a MvcRequestMatcher.Builder instance as a parameter, iterates through the array of excluded URLs, and creates a RequestMatcher for each URL pattern using the provided MvcRequestMatcher.Builder. The resulting array of RequestMatcher instances is returned.
	private RequestMatcher[] getMatchers(MvcRequestMatcher.Builder mvc) {

			return Arrays.stream(this.provider.getJwtExcludedUrls())
					.map(url -> mvc.pattern(url))
					.toArray(RequestMatcher[]::new);
	}


	@Bean("test")
	SecurityFilterChain securityFilterChainTest(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {

		// http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)): This configures the session management for the application. In this case, it sets the session creation policy to SessionCreationPolicy.STATELESS, which means Spring Security will not create an HTTP session for any incoming requests.
			http.sessionManagement((session) ->
			session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

			// .authorizeHttpRequests((requests) -> ...): This is where request authorization rules are configured. The requests parameter allows us to specify how different requests should be authorized.
			  .authorizeHttpRequests((requests) ->
// // .requestMatchers(PathRequest.toH2Console()).permitAll(): This line allows access to the H2 console (used for the H2 in-memory database) without any authentication. It uses the PathRequest.toH2Console() method to match the request path for the H2 console and then permits all requests without requiring any authentication.
			  requests.requestMatchers(PathRequest.toH2Console())
			  .permitAll()
// 				  //Requests matching the patterns returned by the getMatchers(mvc) method are also permitted without authentication.
			  .requestMatchers(this.getMatchers(mvc)).permitAll()
			  .requestMatchers(this.getMatchers(mvc)).permitAll()
// .anyRequest().authenticated(): This rule specifies that any other request (not matching the previous rules) should be authenticated, meaning the user needs to be logged in with valid credentials to access these requests.
			  .anyRequest().authenticated())
// This configures the exception handling for authentication failures. The customAuthEntryPoint is an instance of a custom authentication entry point, which is responsible for handling authentication failures and returning appropriate responses to the client.
	          .exceptionHandling((handler)-> handler.authenticationEntryPoint(this.customAuthEntryPoint))
// This adds a custom filter (jwtAuthorizationFilter) before the UsernamePasswordAuthenticationFilter. Custom filters can perform additional processing on incoming requests or outgoing responses. In this case, the jwtAuthorizationFilter is used to handle JWT (JSON Web Token) based authentication.
	          .addFilterBefore(this.jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
// // This configures the HTTP response headers related to security. In this case, it sets the X-Frame-Options header to SAMEORIGIN, which helps to prevent the application from being embedded in frames from other domains, providing a basic level of protection against clickjacking attacks.
	          .headers((headers) -> headers.frameOptions((frame) -> frame.sameOrigin()))
// This configures Cross-Origin Resource Sharing (CORS) support with default settings. CORS is used to control access to resources from different domains.
	          .cors(withDefaults())
// This disables Cross-Site Request Forgery (CSRF) protection. CSRF protection is typically enabled by default in Spring Security, but in this case, it is explicitly disabled (csrf.disable()) for our application.
	          .csrf((csrf) -> csrf.disable());

// return http.build(): This returns the HttpSecurity object after applying all the configurations specified above. The HttpSecurity object has been configured with various security settings based on the rules and filters provided in the method.
	        return http.build();
	}


	@Bean
	@ConditionalOnMissingBean(SecurityFilterChain.class)
	SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {

			http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			  .authorizeHttpRequests((requests) -> requests.requestMatchers(this.getMatchers(mvc)).permitAll()
	                                                      .anyRequest().authenticated())
	          .exceptionHandling((handler)-> handler.authenticationEntryPoint(this.customAuthEntryPoint))
	          .addFilterBefore(this.jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
	          .cors(withDefaults())
	          .csrf((csrf) -> csrf.disable());

			return http.build();
	}

}