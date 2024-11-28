package com.xpinjection.library.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class ActuatorBasicSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, WebEndpointProperties webEndpointProperties) throws Exception {
        var actuatorBasePath = webEndpointProperties.getBasePath();
        return http.authorizeHttpRequests(requests ->
                        requests.requestMatchers(actuatorBasePath + "/health/**").permitAll()
                                .requestMatchers(actuatorBasePath + "/**").hasRole("ADMIN")
                                .requestMatchers("/**").permitAll()
                                .anyRequest().authenticated())
            .httpBasic(withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .csrf(AbstractHttpConfigurer::disable)
            .build();
    }
}