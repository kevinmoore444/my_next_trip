package org.example.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@ConditionalOnWebApplication
public class SecurityConfig {

    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration config) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.POST, "/authenticate").permitAll()
                .requestMatchers(HttpMethod.POST, "/create_account").permitAll()
                .requestMatchers(HttpMethod.POST, "/refresh").authenticated()
                .requestMatchers("/api/city/user-list/**").hasAuthority("USER")
                .requestMatchers("/api/country/user-list/**").hasAuthority("USER")
                .requestMatchers("/api/attraction/user-list/**").hasAuthority("USER")
                .requestMatchers("/api/attraction/user/**").hasAuthority("ADMIN")
                .requestMatchers("/api/city/user/**").hasAuthority("ADMIN")
                .requestMatchers("/api/country/user/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
        );

        http.addFilter(new JwtRequestFilter(manager(config), converter));

        http.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}