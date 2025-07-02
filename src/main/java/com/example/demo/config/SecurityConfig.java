package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.handlers.CustomOAuth2SuccessHandler;
import com.example.demo.handlers.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  public SecurityConfig(CustomOAuth2SuccessHandler customOAuth2SuccessHandler, JwtAuthenticationFilter jwtAuthenticationFilter) {
    this.customOAuth2SuccessHandler = customOAuth2SuccessHandler;
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http
          .csrf(AbstractHttpConfigurer::disable)
          .cors(Customizer.withDefaults())
          .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authorizeHttpRequests(authz -> authz
              .requestMatchers("/", "/public/**", "/user", "/oauth2/**", "/v3/api-docs/**", "/swagger-ui/**",
                      "/swagger-ui.html", "/new-role", "/api/token/refresh", "/actuator/health", "/actuator/info",
                      "/actuator/prometheus", "/wompi/event", "/reviews/*").permitAll()
              .anyRequest().authenticated()
          )
          .oauth2Login(oauth2 -> oauth2
              .successHandler(customOAuth2SuccessHandler)
          ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

      return http.build();
  }
}