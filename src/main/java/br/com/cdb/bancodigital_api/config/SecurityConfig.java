package br.com.cdb.bancodigital_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // desativa CSRF completamente
                .headers(headers -> headers.frameOptions(frame -> frame.disable())) // permite H2
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/clientes/**").permitAll()
                        .requestMatchers("/contas/**").permitAll()
                        .anyRequest().authenticated() // exige autenticação nos demais
                )
                .build();
    }
}
