package com.camilogaray.tasksapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AppSecurity {

    @Bean
    public InMemoryUserDetailsManager userDetails() {

        UserDetails bob = User.builder().username("bob").password("{noop}1234").roles("EMPLOYEE").build();
        UserDetails alice = User.builder().username("alice").password("{noop}1234").roles("EMPLOYEE", "MANAGER").build();
        UserDetails david = User.builder().username("david").password("{noop}1234").roles("EMPLOYEE", "MANAGER", "ADMIN").build();

        return new InMemoryUserDetailsManager(bob, alice, david);
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(conf -> conf
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/authenticate")
                        .permitAll());
        return http.build();
    }

}
