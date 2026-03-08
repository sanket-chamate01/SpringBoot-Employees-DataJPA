package com.rest.employee.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        // JdbcUserDetailsManager - tells spring security to use JDBC authentication with our data source
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);


        return jdbcUserDetailsManager;
    }

    // hard-coded user details
    /*
    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){

        UserDetails john = User.builder()
                .username("john")
                .password("{noop}test123") // bcrypt version - $2a$10$HdmZZUstFgqDTsW7RpXrBeVMFSaIztaQtAymLvjWkMNrcfhxhYsmK
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
     */

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
