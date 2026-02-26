package com.alura.alura_foro.config;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Desactivar CSRF
                .cors(cors -> cors.disable())  // Desactivar CORS (para pruebas)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/topicos/**").permitAll()  // Permitir todas las rutas de /topicos
                        .requestMatchers("/actuator/**").permitAll()  // Si usas actuator
                        .anyRequest().permitAll()  // Permitir TODO temporalmente
                );
        return http.build();
    }
}