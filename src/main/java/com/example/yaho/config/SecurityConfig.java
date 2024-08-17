//package com.example.yaho.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@RequiredArgsConstructor
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests((authorizeRequests) -> {
//                    authorizeRequests.anyRequest().permitAll();
//                });
//
//        return http.build();
//    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer(){
//        return web ->
//                web.ignoring().requestMatchers("/swagger", "/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**", "/v3/api-docs/**");
//    }
//}
//
//
//
////        http
////                .authorizeHttpRequests((auth) -> auth
////                        .requestMatchers( "/swagger-resources",
////                        "/swagger-resources/**", "/configuration/ui", "/configuration/security",
////                        "/webjars/**", ).permitAll()
////                        .anyRequest().authenticated()); // 일단 임시로 허용