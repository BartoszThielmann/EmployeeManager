package com.bartoszthielmann.employeemanager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // this uses the default schema that Spring Boot expects
    @Bean
    public UserDetailsService userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/employees/list").hasRole("USER")
                        .requestMatchers("/employees/**").hasRole("ADMIN")
                        .requestMatchers("/offices/list").hasRole("USER")
                        .requestMatchers("/offices/**").hasRole("ADMIN")
                        .requestMatchers("/reservations/**").hasRole("USER")
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
