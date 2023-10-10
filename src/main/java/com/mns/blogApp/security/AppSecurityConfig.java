package com.mns.blogApp.security;


import com.mns.blogApp.users.UsersService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {


    private JWTAuthenticationFilter jwtAuthenticationFilter;
    private JWTService jwtService;
    private UsersService usersService;

    public AppSecurityConfig(JWTService jwtService, UsersService usersService) {
        this.jwtService = jwtService;
        this.usersService = usersService;
        this.jwtAuthenticationFilter = new JWTAuthenticationFilter(
                new JWTAuthenticationManager(jwtService, usersService));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.disable());
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests((request) -> request.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/swagger-ui/*")).permitAll());
        http.authorizeHttpRequests((request) -> request.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/v3/api-docs/*")).permitAll());
        http.authorizeHttpRequests((request) -> request.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/v3/api-docs")).permitAll());
        http.authorizeHttpRequests((request) -> request.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/users/signup")).permitAll());
        http.authorizeHttpRequests((request) -> request.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/users/login")).permitAll());
        http.authorizeHttpRequests((request) -> request.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/articles")).permitAll());
        http.authorizeHttpRequests((request) -> request.anyRequest().authenticated());
        http.addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class);
        return http.build();
    }
}
