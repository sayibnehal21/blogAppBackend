package com.mns.blogApp.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AppSecurityConfig {


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
//        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
//        http.cors(cors -> cors.disable());
//        http.csrf(csrf -> csrf.disable());
////        http.authorizeHttpRequests((request) -> request.requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/*")).permitAll());
//        http.authorizeHttpRequests((request) -> request.requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/users/signup")).permitAll());
//        http.authorizeHttpRequests((request) -> request.requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/users/login")).permitAll());
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.disable());
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests((request) -> request.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/swagger-ui/*")).permitAll());
        http.authorizeHttpRequests((request) -> request.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/v3/api-docs/*")).permitAll());
        http.authorizeHttpRequests((request) -> request.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/v3/api-docs")).permitAll());
        http.authorizeHttpRequests((request) -> request.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/users/signup")).permitAll());
        http.authorizeHttpRequests((request) -> request.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/users/login")).permitAll());
        http.authorizeHttpRequests((request) -> request.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/h2-console/*")).permitAll());
        http.authorizeHttpRequests((request) -> request.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/h2-console/*")).permitAll());
        http.authorizeHttpRequests((request) -> request.anyRequest().authenticated());
        return http.build();
    }


}
