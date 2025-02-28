package com.camilogaray.tasksapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class AppSecurity {

    @Bean
    public UserDetailsManager userDetails(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(conf -> conf
                        .requestMatchers("/").hasRole("EMPLOYEE")
                        .requestMatchers("/managers/**").hasRole("MANAGER")
                        .requestMatchers("/admins/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/authenticate")
                        .permitAll())
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling(conf -> conf
                        .accessDeniedPage("/unauthorized"));
        return http.build();
    }

}
