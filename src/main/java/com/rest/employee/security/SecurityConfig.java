package com.rest.employee.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){

        UserDetails john = User.builder()
                .username("john")
                .password("{noop}test123")
                .roles(RoleConstants.EMPLOYEE)
                .build();

        UserDetails mary = User.builder()
                .username("mary")
                .password("{noop}test123")
                .roles(RoleConstants.MANAGER)
                .build();

        UserDetails susan = User.builder()
                .username("susan")
                .password("{noop}test123")
                .roles(RoleConstants.ADMIN)
                .build();

        return new InMemoryUserDetailsManager(john, mary, susan);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/employees")
                            .hasAnyRole(
                                    RoleConstants.EMPLOYEE,
                                    RoleConstants.ADMIN,
                                    RoleConstants.MANAGER)
                        .requestMatchers(HttpMethod.GET, "/api/employees/**")
                            .hasAnyRole(
                                    RoleConstants.EMPLOYEE,
                                    RoleConstants.ADMIN,
                                    RoleConstants.MANAGER)
                        .requestMatchers(HttpMethod.POST, "/api/employees/save")
                            .hasAnyRole(
                                    RoleConstants.MANAGER,
                                    RoleConstants.ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/employees/update")
                            .hasAnyRole(
                                    RoleConstants.MANAGER,
                                    RoleConstants.ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/api/employees/**")
                            .hasRole(RoleConstants.ADMIN));

        // http basic authentication
        httpSecurity.httpBasic(Customizer.withDefaults());

        // disable csrf(cross site request forgery)
        httpSecurity.csrf(csrf -> csrf.disable()); // generally, csrf is not required for stateless POST/PUT/PATCH/DELETE

        return httpSecurity.build();
    }
}
