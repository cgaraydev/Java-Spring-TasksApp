package com.camilogaray.tasksapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class AppSecurity {

    @Bean
    public UserDetailsManager userDetails(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .expiredUrl("/login")
                        .maxSessionsPreventsLogin(false))
                        .authorizeHttpRequests(conf -> conf
                                .requestMatchers("/").authenticated()
                                .requestMatchers("/managers/**").hasRole("MANAGER")
                                .requestMatchers("/admins/**").hasRole("ADMIN")
                                .anyRequest().authenticated())
                        .formLogin(form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/index")
                                .defaultSuccessUrl("/index", true)
                                .permitAll())
                        .logout(LogoutConfigurer::permitAll)
                        .exceptionHandling(conf -> conf
                                .accessDeniedPage("/unauthorized"));
        return http.build();
    }


}
