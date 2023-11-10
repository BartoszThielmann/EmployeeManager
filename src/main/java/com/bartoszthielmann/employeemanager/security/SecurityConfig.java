package com.bartoszthielmann.employeemanager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/employees/list").hasRole("EMPLOYEE")
                        .requestMatchers("/employees/**").hasRole("ADMIN")
                        .requestMatchers("/offices/list").hasRole("EMPLOYEE")
                        .requestMatchers("/offices/**").hasRole("ADMIN")
                        .requestMatchers("/reservations/**").hasRole("EMPLOYEE")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                )
                .logout((logout) ->
                        logout
                            .logoutSuccessUrl("/"))
                .httpBasic(withDefaults());
        return http.build();
    }
}
