package com.toyota.security.config;

import com.toyota.security.filter.JwtAuthFilter;
import com.toyota.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userService;
    private final JwtAuthFilter jwtAuthFilter;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        return http
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/security/generateToken","/security/validateToken").permitAll()
//                        .requestMatchers("employee/**","/security/deneme/**").permitAll()
                        .requestMatchers("/security/manager/**").hasAnyRole("MANAGER","ADMIN")
                        .requestMatchers("/security/admin/**").hasAnyRole("ADMIN")
                        .requestMatchers("/security/cashier/**").hasAnyRole("CASHIER","ADMIN")
                        .requestMatchers("/security/sales/**").hasAnyRole("CASHIER","ADMIN")
                        .requestMatchers("/security/campaigns/**").hasAnyRole("CASHIER","ADMIN")
                        .requestMatchers("/security/product/**").hasAnyRole("CASHIER","ADMIN")
                        .requestMatchers("/security/employee/**").hasAnyRole("ADMIN")
                        .requestMatchers("/security/role/**").hasAnyRole("CASHIER","ADMIN")
                        .requestMatchers("/security/report/**").hasAnyRole("CASHIER","ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();

    }
}
