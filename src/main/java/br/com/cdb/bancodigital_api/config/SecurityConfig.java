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
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/clientes/**").permitAll()
                        .requestMatchers("/contas/**").permitAll()
                        .requestMatchers("/cartoes/**").permitAll()
                        .anyRequest().permitAll()
                )
                .build(); // <- Faltava o build aqui
    }
}
