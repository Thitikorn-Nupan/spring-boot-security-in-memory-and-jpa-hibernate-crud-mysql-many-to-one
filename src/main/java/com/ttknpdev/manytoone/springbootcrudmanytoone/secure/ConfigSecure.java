package com.ttknpdev.manytoone.springbootcrudmanytoone.secure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ConfigSecure {
    // user , employee , manager
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails role1 = User
                .withUsername("A")
                .password("{noop}12345")
                .roles("USER")
                .build();
        // user can access
        // [endpoint programmer]   /read/** ,  /reads , /create
        // [endpoint performance] /create/programmer/** , /reads/programmer/** , /read/programmer/**

        UserDetails role2 = User
                .withUsername("B")
                .password("{noop}12345")
                .roles("EMPLOYEE")
                .build();
        // employee can access
        // [endpoint programmer]   /read/** ,  /reads , /create , /update/**
        // [endpoint performance] /create/programmer/** , /reads/programmer/** , /read/programmer/** , /update/programmer/**
        UserDetails role3 = User
                .withUsername("C")
                .password("{noop}12345")
                .roles("MANAGER")
                .build();
        // manager can access
        // [endpoint programmer]   anything
        // [endpoint performance]  anything
        return new InMemoryUserDetailsManager(role1,role2,role3);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET,"/api/programmer/reads").hasAnyRole("USER","EMPLOYEE","MANAGER")
                .requestMatchers(HttpMethod.GET,"/api/programmer/read/**").hasAnyRole("USER","EMPLOYEE","MANAGER")
                .requestMatchers(HttpMethod.GET,"/api/performance/reads/programmer/**").hasAnyRole("USER","EMPLOYEE","MANAGER")
                .requestMatchers(HttpMethod.GET,"/api/performance/read/programmer/**").hasAnyRole("USER","EMPLOYEE","MANAGER")
                .requestMatchers(HttpMethod.POST,"/api/programmer/create").hasAnyRole("USER","EMPLOYEE","MANAGER")
                .requestMatchers(HttpMethod.POST,"/api/performance/create/programmer/**").hasAnyRole("USER","EMPLOYEE","MANAGER")
                .requestMatchers(HttpMethod.PUT,"/api/programmer/update/**").hasAnyRole("EMPLOYEE","MANAGER")
                .requestMatchers(HttpMethod.PUT,"/api/performance/update/programmer/**").hasAnyRole("EMPLOYEE","MANAGER")
                .requestMatchers(HttpMethod.DELETE,"/api/programmer/delete/**").hasRole("MANAGER")
                .requestMatchers(HttpMethod.DELETE,"/api/performance/delete/programmer/**").hasRole("MANAGER")
                .anyRequest().authenticated();

        httpSecurity.csrf().disable();
        httpSecurity.formLogin().disable(); // close default form html
        httpSecurity.httpBasic(); // open auth on header

        return httpSecurity.build();
    }
}
