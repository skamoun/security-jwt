package com.kamoun.security.config;

import jakarta.servlet.Filter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityFilterChain {
    private final  JwtAuthenticationFilter jwtAuthFilter;
    private final  AuthenticationProvider authenticationProvider;

    @ Bean
    public DefaultSecurityFilterChain securityFilterChaintyFil(HttpSecurity http) throws Exception {
       DefaultSecurityFilterChain filterChain =
               http.csrf()
                       .disable()
               .authorizeHttpRequests()
               .requestMatchers("")
               .permitAll()
               .anyRequest()
               .authenticated()
               .and()
                       .sessionManagement()
                       .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                       .and()
                       .authenticationProvider(authenticationProvider)
                       .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)// required auth per request
               .build();
       return  filterChain;
    }
}
